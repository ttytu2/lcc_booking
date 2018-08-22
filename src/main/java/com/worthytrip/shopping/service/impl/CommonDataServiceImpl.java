package com.worthytrip.shopping.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.worthytrip.commons.http.client.HttpClient;
import com.worthytrip.commons.http.client.module.HeaderBody;
import com.worthytrip.shopping.dao.mapper.flightdata.CacheDataMapper;
import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;
import com.worthytrip.shopping.dao.model.flightdata.FixedFlight;
import com.worthytrip.shopping.dao.model.flightdata.OutTimeRequestModel;
import com.worthytrip.shopping.execution.config.BaseConfig;
import com.worthytrip.shopping.service.ICityCodeService;
import com.worthytrip.shopping.service.ICommonDataService;
import com.worthytrip.shopping.service.IRedisService;
import com.worthytrip.shopping.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CommonDataServiceImpl implements ICommonDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IRedisService redisService;
    @Autowired
    private ICityCodeService cityCodeService;
    @Autowired
    private CacheDataMapper cacheDataMapper;

    @Autowired
    private BaseConfig config;

    @Override
    public int saveCacheData(CacheDataModel cacheData) {
        return cacheDataMapper.saveCacheData(cacheData);

    }

    /**
     * 调用爬虫抓取数据，成功则返回，超时则加入超时队列
     */

    @Override
    public String grabData(String json, String redisKey, CacheDataModel cacheDataModel, String redisKeyBlack,
                           Integer timeOut, int grabType) {
        String resultJson = null;
        cacheDataModel.setGrapType(grabType);
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        Header[] headers = {new BasicHeader(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_CONTENT_TYPE_VALUE)};
        String postResult;
        if (timeOut == null) {
            // 爬虫的抓取超时时间为40s，所以默认的10s不行
            postResult = HttpClient.executeByPostWithoutGzipWithHeaders(config.getGrapUrl(), json, Integer.valueOf(
                    config.getTimeout()), Integer.valueOf(config.getTimeout()), Integer.valueOf(config.getTimeout()), headers).getData();
        } else {
            // 爬虫的抓取超时时间为40s，所以默认的10s不行
            HeaderBody grabBody = HttpClient.executeByPostWithoutGzipWithHeaders(config.getGrapUrl(), json, timeOut, timeOut, timeOut, headers);
            if (grabBody == null) {
                return null;
            } else {
                postResult = grabBody.getData();
            }
        }
        JSONObject grabResult;
        //请求python超时会返回null
        try {
            grabResult = JSONObject.parseObject(postResult);
        } catch (Exception e) {
            grabResult = JSONObject.parseObject("{\"status\":3}");
        }
        logger.info("{}:Grab Status:{}", redisKey, grabResult.get(Constants.STATUS).toString());
        // 抓取成功
        if (Constants.STRING_ZERO.equals(grabResult.get(Constants.STATUS).toString())) {
            int seatsRemain;
            List<Integer> seatsRemainList = new ArrayList<>();
            List<JSONObject> flightObjectList = StringUtil.getJSONObjectListFromArrayStr(grabResult.get(
                    Constants.ROUTINGS).toString());
            if (flightObjectList.size() > 0) {
                for (JSONObject flight : flightObjectList) {
                    List<JSONObject> fromLineObjectList = StringUtil.getJSONObjectListFromArrayStr(flight.get(
                            Constants.FROMSEGMENTS).toString());
                    for (JSONObject line : fromLineObjectList) {
                        if (Constants.STRING_EMPTY.equals(line.get(Constants.SEATSREMAIN).toString())) {
                            seatsRemainList.add(9);
                        } else {
                            seatsRemainList.add(Integer.valueOf(line.get(Constants.SEATSREMAIN).toString()));
                        }
                    }

                    if (flight.containsKey(Constants.RETSEGMENTS)) {

                        List<JSONObject> backLineObjectList = StringUtil.getJSONObjectListFromArrayStr(flight.get(
                                Constants.RETSEGMENTS).toString());
                        for (JSONObject line : backLineObjectList) {
                            if (Constants.STRING_EMPTY.equals(line.get(Constants.SEATSREMAIN).toString())) {
                                seatsRemainList.add(9);
                            } else {
                                seatsRemainList.add(Integer.valueOf(line.get(Constants.SEATSREMAIN).toString()));
                            }
                        }
                    }

                }
                // 排序获取最小座位数
                Collections.sort(seatsRemainList);
                seatsRemain = seatsRemainList.get(0);

            } else {
                // 无航班时 刷新时间延长
                seatsRemain = 100;
            }
            endTime = System.currentTimeMillis();
            cacheDataModel.setGrapTime(Integer.valueOf(String.valueOf(endTime - startTime)));
            cacheDataModel.setGrapStatus(0);
            cacheDataModel.setResultJson(grabResult.get(Constants.ROUTINGS).toString());
            cacheDataModel.setSurplusNumber(seatsRemain);
            // 将数据放入数据库
            int i = this.saveCacheData(cacheDataModel);
            resultJson = StringUtil.conversionForBeRequiredOnPriceInfo(grabResult.get(Constants.ROUTINGS).toString(),
                    cityCodeService, JSONObject.parseObject(json).getString(Constants.IPCC));
            if (i == 1) {
                logger.info("Grab Success  And Deposit in  Redis:{},GrabType:{}", redisKey, cacheDataModel.getGrapType());
                // 数据库中存爬虫抓取的信息，redis中存转换之后的数据
                cacheDataModel.setResultJson(resultJson);
                // 将数据放入redis并设置3600秒
                redisService.set(redisKey, cacheDataModel, config.getCacheTimeOut());
            }
        }

        if (!Constants.STRING_ZERO.equals(grabResult.get(Constants.STATUS).toString())) {
            endTime = System.currentTimeMillis();
            cacheDataModel.setGrapStatus(Integer.valueOf(grabResult.get(Constants.STATUS).toString()));
            cacheDataModel.setGrapTime(Integer.valueOf(String.valueOf(endTime - startTime)));
            cacheDataModel.setResultJson(StringUtils.EMPTY);
            this.saveCacheData(cacheDataModel);
            //返回状态码为2 加入缓存队列
            if (Constants.STRING_TWO.equals(grabResult.get(Constants.STATUS).toString())) {
                JSONObject fixedFlightJson = JSONObject.parseObject(json);
                FixedFlight fixedFlight = new FixedFlight();
                Integer flightOption = Constants.FlightOption.ROUND_TRIP.getValue().equals(fixedFlightJson.getString(Constants.FLIGHTOPTION)) ? 1 : 0;
                fixedFlight.setAdultnumber(fixedFlightJson.getIntValue(Constants.ADULTNUMBER));
                fixedFlight.setChildnumber(fixedFlightJson.getIntValue(Constants.CHILDNUMBER));
                fixedFlight.setFlightoption(flightOption);
                fixedFlight.setFromcity(cityCodeService.getCityCodeFromAirportCode(fixedFlightJson.getString(Constants.FROMCITY)));
                fixedFlight.setTocity(cityCodeService.getCityCodeFromAirportCode(fixedFlightJson.getString(Constants.TOCITY)));
                fixedFlight.setRetdate(DateFormat.moreThanToday(fixedFlightJson.getString(Constants.RETDATE)));
                fixedFlight.setStartdate(DateFormat.moreThanToday(fixedFlightJson.getString(Constants.STARTDATE)));
                PrivateData.NEED_GRAB_CACHE.putIfAbsent(fixedFlight, 1);
            }
        }
        return resultJson;
    }

    /**
     * 查询数据
     */
    @Override
    public JSONObject queryData(CacheDataModel cacheDataModel) {

        //不响应出发日期在5天之内的请求,直接返回一个空routings
        if (DateFormat.moreThanToday(cacheDataModel.getStartDate()) <= Constants.INT_FOUR) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", Constants.INT_ONE);
            jsonObject.put("message", Constants.SUCCESS);
            jsonObject.put("routings", Constants.BLANK_ROUTINGS);

            return jsonObject;
        }

        JSONObject result = new JSONObject();
        // 接口调用状态，0表示错误，1表示成功
        int status;
        // 返回错误信息
        String stateMessage;
        long startTime = System.currentTimeMillis();
        String flightOption = cacheDataModel.getFlightOption();
        String fromCity = cacheDataModel.getFromCity();
        String toCity = cacheDataModel.getToCity();
        String startDate = cacheDataModel.getStartDate();
        String retDate = cacheDataModel.getRetDate();
        int adultNumber = cacheDataModel.getAdultNumber();
        int childNumber = cacheDataModel.getChildNumber();
        String redisKey = RoutingUtil.createRedisKeyByEntity(cacheDataModel);
        if (StringUtil.isNullOrBlank(flightOption) || StringUtil.isNullOrBlank(fromCity) || StringUtil.isNullOrBlank(
                toCity) || StringUtil.isNullOrBlank(startDate) || StringUtil.isNullOrBlank(retDate) || adultNumber < 1) {
            status = 0;
            stateMessage = "参数错误";
            logger.warn("Query Data:参数有误或不完整");
        } else {
            String redisKeyBlack = fromCity + "-" + toCity + "-" + "black";
            OutTimeRequestModel outTimeRequestModelRedis = (OutTimeRequestModel) redisService.get(redisKeyBlack);
            if (outTimeRequestModelRedis != null) {
                status = 0;
                stateMessage = "航线黑名单";
                logger.warn("Query Data:IPCC:{},Black List Exist:{}---{}", cacheDataModel.getIpcc(), fromCity, toCity);
            } else {
                JSONObject grabRequest = new JSONObject();
                grabRequest.put(Constants.CID, Constants.CID_VALUE);
                grabRequest.put(Constants.FROMCITY, cityCodeService.getAirportCodeFromCityCode(fromCity, cacheDataModel.getIpcc()));
                grabRequest.put(Constants.TOCITY, cityCodeService.getAirportCodeFromCityCode(toCity, cacheDataModel.getIpcc()));
                grabRequest.put(Constants.STARTDATE, startDate);
                grabRequest.put(Constants.RETDATE, retDate);
                grabRequest.put(Constants.FLIGHTOPTION, flightOption);
                grabRequest.put(Constants.ADULTNUMBER, adultNumber);
                grabRequest.put(Constants.CHILDNUMBER, childNumber);
                grabRequest.put(Constants.DS, Constants.DS_VALUE);
                grabRequest.put(Constants.IPCC, cacheDataModel.getIpcc());
                grabRequest.put(Constants.PARSER, config.getParser());
                grabRequest.put(Constants.BROWSER, config.getBrowser());
                grabRequest.put(Constants.ENTRY, config.getEntry());
                CacheDataModel cacheDataModelRedis = (CacheDataModel) redisService.get(redisKey);
                if (cacheDataModelRedis == null) {
                    String json = grabRequest.toString();
                    cacheDataModel.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    String refreshTime = String.valueOf(System.currentTimeMillis());
                    cacheDataModel.setRefreshTime(refreshTime);
                    // 调用爬虫抓取数据
                    String resultJson = this.grabData(json, redisKey, cacheDataModel, redisKeyBlack, null, 0);
                    if (resultJson == null) {
                        status = 0;
                        stateMessage = "抓取数据超时或未被处理";
                        logger.warn("RedisKey:{}.Query Failed ,Status: 0,(Grab Data)", redisKey);
                    } else {
                        status = 1;
                        stateMessage = "查询数据成功";
                        JSONArray originRoutings = JSONArray.parseArray(resultJson);
                        JSONArray flightRoutings = RoutingUtil.filterRoutingsBySeats(originRoutings, config.getFilterSeatsCount());
                        result.put("routings", flightRoutings);
                        logger.info("RedisKey:{}:Query Success,Status :1(Grab Data)", redisKey);

                    }

                } else {
                    status = 1;
                    stateMessage = "查询数据成功";
                    JSONArray originRoutings = JSONArray.parseArray(cacheDataModelRedis.getResultJson());
                    JSONArray flightRoutings = RoutingUtil.filterRoutingsBySeats(originRoutings, config.getFilterSeatsCount());
                    result.put(Constants.ROUTINGS, flightRoutings);
                    logger.info("RedisKey:{}:Query Success,Status :1(In Redis)", redisKey);
                }
            }
        }
        result.put("status", status);
        result.put("message", stateMessage);
        long endTime = System.currentTimeMillis();
        logger.debug("共耗时：" + (endTime - startTime) + "ms");

        return result;


    }
}
