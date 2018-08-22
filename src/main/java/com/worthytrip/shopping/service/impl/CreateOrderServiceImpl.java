package com.worthytrip.shopping.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;
import com.worthytrip.shopping.dao.model.flightdata.OrderPnr;
import com.worthytrip.shopping.dao.model.flightdata.RequestCreateOrderBean;
import com.worthytrip.shopping.dao.model.flightdata.RequestCreateOrderBean.FromSegmentsBean;
import com.worthytrip.shopping.dao.model.flightdata.RequestCreateOrderBean.RetSegmentsBean;
import com.worthytrip.shopping.execution.config.BaseConfig;
import com.worthytrip.shopping.service.ICommonDataService;
import com.worthytrip.shopping.service.ICreateOrderService;
import com.worthytrip.shopping.service.IOrderPnrService;
import com.worthytrip.shopping.service.IRedisService;
import com.worthytrip.shopping.util.Constants;
import com.worthytrip.shopping.util.RoutingUtil;
import com.worthytrip.shopping.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreateOrderServiceImpl implements ICreateOrderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICommonDataService commonDataService;
    @Autowired
    private IOrderPnrService orderPnrService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private BaseConfig config;


    @Override
    public JSONObject createOeder(RequestCreateOrderBean orderBean) {
        JSONObject result = new JSONObject();
        String message = Constants.SUCCESS;
        int status = 1;
        String redisKey = RoutingUtil.createRedisKeyByEntity(orderBean);
        //判断前30分钟是否有生单
        if (this.checkOrderExistence(orderBean)) {
            logger.info("Create Order Error({}):30分钟内不允许同天同航班生单", redisKey);
            result.put("message", "30分钟内不允许同天同航班生单");
            result.put("status", 0);
            return result;
        }
        // 抓取数据顺便存储在数据库以及redis
        if (!this.checkPassengerCount(orderBean)) {
            logger.info("Create Order Error({}):生单超过配置最大值", redisKey);
            result.put("status", 0);
            result.put("message", "生单人数过多");
            return result;
        }

        String flightOption = orderBean.getFlightOption();
        String depAirport = orderBean.getFromSegments().get(0).getDepAirport();
        String arrAirport = orderBean.getFromSegments().get(orderBean.getFromSegments().size() - 1).getArrAirport();
        String startDate = orderBean.getStartTime();
        String retDate = Constants.NORETDATE;
        // 用于指向抓取的所有航线中与生单请求对应的那个航线
        JSONObject rightRouting = null;
        // 往返类型才会有retgoTime
        if (Constants.FlightOption.ROUND_TRIP.getValue().equals(flightOption)) {
            retDate = orderBean.getEndTime();
        }
        int adultNumber = Integer.valueOf(orderBean.getAdultNumber());
        int childNumber = Integer.valueOf(orderBean.getChildNumber());
        if (StringUtil.isNullOrBlank(flightOption) || StringUtil.isNullOrBlank(depAirport) || StringUtil.isNullOrBlank(
                arrAirport) || StringUtil.isNullOrBlank(startDate) || StringUtil.isNullOrBlank(retDate) || adultNumber < 1) {
            logger.info("Create Order Error({}):请求参数错误", redisKey);
            status = 0;
            message = "参数错误";
        }
        if (StringUtil.checkCardExpireDate(orderBean)) {
            logger.info("Create Order Error({}):证件有效期不足半年", redisKey);
            status = 0;
            message = "生单失败";
        } else {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put(Constants.CID, Constants.CID_VALUE);
            jsonRequest.put(Constants.FROMCITY, depAirport);
            jsonRequest.put(Constants.TOCITY, arrAirport);
            jsonRequest.put(Constants.STARTDATE, startDate);
            jsonRequest.put(Constants.RETDATE, retDate);
            jsonRequest.put(Constants.FLIGHTOPTION, flightOption);
            jsonRequest.put(Constants.ADULTNUMBER, adultNumber);
            jsonRequest.put(Constants.CHILDNUMBER, childNumber);
            jsonRequest.put(Constants.DS, Constants.DS_VALUE);
            jsonRequest.put(Constants.IPCC, orderBean.getIpcc());
            jsonRequest.put(Constants.PARSER, config.getParser());
            jsonRequest.put(Constants.BROWSER, config.getBrowser());
            // 拼接抓取数据的请求json
            jsonRequest.put(Constants.ENTRY, config.getEntry());
            String redisKeyBlack = depAirport + "-" + arrAirport + "-" + "black";
            String json = jsonRequest.toString();

            CacheDataModel cacheDataModel = new CacheDataModel();
            cacheDataModel.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            String refreshTime = String.valueOf(System.currentTimeMillis());
            cacheDataModel.setAdultNumber(adultNumber);
            cacheDataModel.setChildNumber(childNumber);
            cacheDataModel.setFlightOption(flightOption);
            cacheDataModel.setFromCity(depAirport);
            cacheDataModel.setRefreshTime(refreshTime);
            cacheDataModel.setRetDate(retDate);
            cacheDataModel.setStartDate(startDate);
            cacheDataModel.setToCity(arrAirport);
            cacheDataModel.setIpcc(orderBean.getIpcc());
            String resultJson = null;
            String pnrCode = orderPnrService.getUniquePnrCode();
            OrderPnr orderPnr = new OrderPnr();
            orderPnr.setAdultnumber(adultNumber);
            orderPnr.setChildnumber(childNumber);
            orderPnr.setCreatetime(StringUtil.getNow());
            orderPnr.setPnrcode(pnrCode);
            orderPnr.setSessionid(orderBean.getSessionId());
            orderPnr.setFromCity(orderBean.getFromCity());
            orderPnr.setToCity(orderBean.getToCity());
            orderPnr.setIpcc(orderBean.getIpcc());

            try {
                // 调用爬虫抓取数据
                resultJson = commonDataService.grabData(json, redisKey, cacheDataModel, redisKeyBlack, Integer
                        .valueOf(config.getTimeOutForBooking()), Constants.GrapType.ORDER.getCode());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            // 抓取強制8S超時从缓存获取数据
            if (resultJson == null) {
                // 从缓存中取值
                CacheDataModel cacheDataModelRedis = (CacheDataModel) redisService.get(redisKey);
                if (cacheDataModelRedis == null) {
                    message = "生单失败";
                    status = 0;
                    logger.info("Create Order Error({}):实时爬虫超时且缓存没有航线数据", redisKey);
                } else {
                    resultJson = cacheDataModelRedis.getResultJson();
                    logger.info("Create Order ({}):从缓存中获取数据成功", redisKey);
                    List<JSONObject> flightObjectList = StringUtil.getJSONObjectListFromArrayStr(resultJson);
                    if (flightObjectList.size() > 0) {
                        for (JSONObject routing : flightObjectList) {
                            boolean fromFlag = true;
                            List<JSONObject> fromlineObjectList = StringUtil.getJSONObjectListFromArrayStr(routing.get(
                                    Constants.FROMSEGMENTS).toString());
                            if (orderBean.getFromSegments().size() == fromlineObjectList.size()) {
                                for (int i = 0; i < orderBean.getFromSegments().size(); i++) {
                                    String reqFlightNumber = orderBean.getFromSegments().get(i).getFlightNumber();
                                    String resultFlightNumber = fromlineObjectList.get(i).getString(Constants.FLIGHTNUMBER);
                                    if (!(Integer.parseInt(reqFlightNumber.substring(2)) == Integer.parseInt(resultFlightNumber.substring(2)))) {
                                        fromFlag = false;
                                    }
                                }
                                if (fromFlag) {
                                    rightRouting = routing;
                                }
                            }
                            if (orderBean.getRetSegments() != null && fromFlag && orderBean.getRetSegments().size() > 0) {
                                List<JSONObject> backLineObjectList = StringUtil.getJSONObjectListFromArrayStr(routing
                                        .get(Constants.RETSEGMENTS).toString());
                                // 航段相同
                                if (orderBean.getRetSegments().size() == backLineObjectList.size()) {
                                    boolean retFlag = true;
                                    for (int i = 0; i < orderBean.getRetSegments().size(); i++) {
                                        String reqFlightNumber = orderBean.getRetSegments().get(i).getFlightNumber();
                                        String resultFlightNumber = backLineObjectList.get(i).getString(Constants.FLIGHTNUMBER);
                                        if (!(Integer.parseInt(reqFlightNumber.substring(2)) == Integer.parseInt(resultFlightNumber.substring(2)))) {
                                            retFlag = false;
                                        }
                                    }
                                    if (retFlag) {
                                        rightRouting = routing;
                                    }
                                }
                            }
                        }
                    }
                    if (rightRouting != null) {
                        if (orderBean.getPassengers().size() > rightRouting.getJSONArray(Constants.FROMSEGMENTS).getJSONObject(0).getIntValue(Constants.SEATSREMAIN)) {
                            logger.info("Create Order Error({}):Not Enough Seat", redisKey);
                            result.put("message", "没有足够的座位数");
                            result.put("status", 0);
                            return result;
                        }
                        if (this.checkOrderJumpPrice(rightRouting.getJSONArray("priceInfos"), orderBean)) {
                            logger.info("Create Order Error({}):Jump Price", redisKey);
                            result.put("message", "出现跳价");
                            result.put("status", 0);
                            return result;
                        }

                        String cabins = "";
                        String flightTimes = "";
                        for (FromSegmentsBean from : orderBean.getFromSegments()) {
                            cabins += from.getCabin() + ",";
                            flightTimes += from.getDepTime() + "/" + from.getArrTime() + ",";
                        }
                        if (Constants.FlightOption.ROUND_TRIP.getValue().equals(orderBean.getFlightOption())) {
                            for (RetSegmentsBean ret : orderBean.getRetSegments()) {
                                cabins += ret.getCabin() + ",";
                                flightTimes += ret.getDepTime() + "/" + ret.getArrTime() + ",";
                            }
                        }
                        flightTimes = flightTimes.substring(0, flightTimes.length() - 1);
                        result.put("cabins", cabins.substring(0, cabins.length() - 1));
                        result.put("ds", orderBean.getDs());
                        result.put("flightTimes", flightTimes);
                        result.put("ipcc", orderBean.getIpcc());
                        result.put("lastTicketDate", "");
                        result.put("pnrCode", pnrCode);
                        result.put("pnrId", "");
                        result.put("specialAdultType", orderBean.getSpecialAdultType());
                        JSONArray priceInfos = rightRouting.getJSONArray("priceInfos");
                        for (int i = 0; i < priceInfos.size(); i++) {
                            JSONObject priceInfo = priceInfos.getJSONObject(i);
                            if (priceInfo.getIntValue("baseFare") == 0) {
                                priceInfo.put("baseFare", 1);
                                priceInfo.put("tax", priceInfo.getIntValue("tax") - 1);
                            }
                        }
                        result.put("priceInfos", priceInfos);

                        orderPnrService.saveOrderPnr(orderPnr);
                    } else {
                        logger.info("Create Order Error({}):Not Fount Right Routing", redisKey);
                        message = "已无该航线";
                        status = 0;
                    }
                }
            } else {
                logger.info("Create Order ({}):Grab Data Success", redisKey);
                List<JSONObject> flightObjectList = StringUtil.getJSONObjectListFromArrayStr(resultJson);
                for (JSONObject routing : flightObjectList) {
                    boolean fromFlag = true;
                    List<JSONObject> fromLineObjectList = StringUtil.getJSONObjectListFromArrayStr(routing.get(
                            Constants.FROMSEGMENTS).toString());
                    if (orderBean.getFromSegments().size() == fromLineObjectList.size()) {
                        for (int i = 0; i < orderBean.getFromSegments().size(); i++) {
                            String reqFlightNumber = orderBean.getFromSegments().get(i).getFlightNumber();
                            String resultFlightNumber = fromLineObjectList.get(i).getString(Constants.FLIGHTNUMBER);
                            if (!(Integer.parseInt(reqFlightNumber.substring(2)) == Integer.parseInt(resultFlightNumber.substring(2)))) {
                                fromFlag = false;
                            }
                        }
                        if (fromFlag) {
                            rightRouting = routing;
                        }
                    }
                    if (orderBean.getRetSegments() != null && orderBean.getRetSegments().size() > 0 && fromFlag) {
                        List<JSONObject> backlineObjectList = StringUtil.getJSONObjectListFromArrayStr(routing.get(
                                Constants.RETSEGMENTS).toString());
                        // 航段相同
                        if (orderBean.getRetSegments().size() == backlineObjectList.size()) {
                            boolean retflag = true;
                            for (int i = 0; i < orderBean.getRetSegments().size(); i++) {
                                String reqFlightNumber = orderBean.getRetSegments().get(i).getFlightNumber();
                                String resultFlightNumber = backlineObjectList.get(i).getString(Constants.FLIGHTNUMBER);
                                if (!(Integer.parseInt(reqFlightNumber.substring(2)) == Integer.parseInt(resultFlightNumber.substring(2)))) {
                                    retflag = false;
                                }
                            }
                            if (retflag) {
                                rightRouting = routing;
                            }
                        }
                    }
                }
                if (rightRouting != null) {
                    if (orderBean.getPassengers().size() > rightRouting.getJSONArray(Constants.FROMSEGMENTS).getJSONObject(0).getIntValue(Constants.SEATSREMAIN) - 1) {
                        logger.info("Create Order Error({}):Not Enough Seat", redisKey);
                        result.put("message", "没有足够的座位数");
                        result.put("status", 0);
                        return result;
                    }
                    if (this.checkOrderJumpPrice(rightRouting.getJSONArray("priceInfos"), orderBean)) {
                        logger.info("Create Order Error({}):Jump Price", redisKey);
                        result.put("message", "出现跳价");
                        result.put("status", 0);
                        return result;
                    }
                    String cabins = "";
                    String flightTimes = "";
                    for (FromSegmentsBean from : orderBean.getFromSegments()) {
                        cabins += from.getCabin() + ",";
                        flightTimes += from.getDepTime() + "/" + from.getArrTime() + ",";
                    }
                    if (Constants.FlightOption.ROUND_TRIP.getValue().equals(orderBean.getFlightOption())) {
                        for (RetSegmentsBean ret : orderBean.getRetSegments()) {
                            cabins += ret.getCabin() + ",";
                            flightTimes += ret.getDepTime() + "/" + ret.getArrTime() + ",";
                        }
                    }
                    flightTimes = flightTimes.substring(0, flightTimes.length() - 1);
                    result.put("cabins", cabins.substring(0, cabins.length() - 1));
                    result.put("ds", orderBean.getDs());
                    result.put("flightTimes", flightTimes);
                    result.put("ipcc", orderBean.getIpcc());
                    result.put("lastTicketDate", "");
                    result.put("pnrCode", pnrCode);
                    result.put("pnrId", "");
                    result.put("specialAdultType", orderBean.getSpecialAdultType());
                    JSONArray priceInfos = rightRouting.getJSONArray("priceInfos");
                    for (int i = 0; i < priceInfos.size(); i++) {
                        JSONObject priceInfo = priceInfos.getJSONObject(i);
                        if (priceInfo.getIntValue("baseFare") == 0) {
                            priceInfo.put("baseFare", 1);
                            priceInfo.put("tax", priceInfo.getIntValue("tax") - 1);
                        }
                    }
                    result.put("priceInfos", priceInfos);
                    orderPnrService.saveOrderPnr(orderPnr);
                } else {
                    logger.info("Create Order Error({}):Not Found Right  Routing", redisKey);
                    message = "已无该航线";
                    status = 0;
                }
            }
        }
        result.put("message", message);
        result.put("status", status);
        return result;
    }

    @Override
    public boolean checkOrderExistence(RequestCreateOrderBean requestBody) {

        String redisKey = orderRequestStringSplicing(requestBody);

        return redisService.isExist(redisKey);
    }

    @Override
    public String orderRequestStringSplicing(RequestCreateOrderBean requestbody) {
        StringBuilder str = new StringBuilder();

        String formFlightNumber = obtainFormSegmentsFlightNumbers(requestbody.getFromSegments());
        str.append(requestbody.getFromCity()).append("_").append(requestbody.getToCity()).append(",").append(requestbody
                .getStartTime());
        if (!StringUtil.isNullOrBlank(requestbody.getEndTime())) {
            str.append("_").append(requestbody.getEndTime()).append(",").append(formFlightNumber);
        } else {
            str.append("_").append(Constants.NORETDATE).append(",").append(formFlightNumber);
        }

        if (!StringUtil.isNullOrBlank(requestbody.getFlightOption()) && requestbody.getFlightOption().equalsIgnoreCase(
                Constants.FlightOption.ROUND_TRIP.getValue())) {
            String retFlightNumber = obtainRetSegmentsFlightNumbers(requestbody.getRetSegments());
            str.append(";").append(requestbody.getFromCity()).append("_").append(requestbody.getToCity()).append(",")
                    .append(requestbody.getStartTime()).append("_").append(requestbody.getEndTime()).append(",").append(
                    retFlightNumber);
        }
        return str.toString();
    }

    private String obtainRetSegmentsFlightNumbers(List<RetSegmentsBean> flightSegments) {

        StringBuilder segmentFlightNumber = new StringBuilder();
        for (RetSegmentsBean retSegmentsBean : flightSegments) {
            segmentFlightNumber.append(retSegmentsBean.getFlightNumber()).append("_");
        }
        return segmentFlightNumber.deleteCharAt(segmentFlightNumber.length() - 1).toString();
    }

    private String obtainFormSegmentsFlightNumbers(List<FromSegmentsBean> flightSegments) {
        StringBuilder segmentFlightNumber = new StringBuilder();
        for (FromSegmentsBean formSegmentsBean : flightSegments) {
            segmentFlightNumber.append(formSegmentsBean.getFlightNumber()).append("_");
        }
        return segmentFlightNumber.deleteCharAt(segmentFlightNumber.length() - 1).toString();
    }

    @Override
    public void keepOrderKeyRedis(String str, RequestCreateOrderBean requestbody) {
        JSONObject json = (JSONObject) JSON.toJSON(requestbody);
        String reqStr = json.toString();
        redisService.set(str, reqStr, config.getRedisDeadLine());

    }


    private boolean checkPassengerCount(RequestCreateOrderBean requestbody) {

        int ipccOrderSeats = config.getIpccOrderSeatsLimit(requestbody.getIpcc());
        if (ipccOrderSeats == 0) {
            return true;
        }
        int adultNumber = Integer.parseInt(requestbody.getAdultNumber());

        int childNumber = Integer.parseInt(requestbody.getChildNumber());

        return adultNumber + childNumber <= ipccOrderSeats;

    }

    private boolean checkOrderJumpPrice(JSONArray cachePriceInfos, RequestCreateOrderBean orderBean) {

        List<RequestCreateOrderBean.PriceInfosBean> reqPriceInfos = orderBean.getPriceInfos();
        for (int i = 0; i < cachePriceInfos.size(); i++) {
            for (RequestCreateOrderBean.PriceInfosBean reqPriceInfo : reqPriceInfos) {
                JSONObject cachePriceinfo = cachePriceInfos.getJSONObject(i);
                if (StringUtils.equals(cachePriceinfo.getString("passengerType"), reqPriceInfo.getPassengerType())) {
                    if ((cachePriceinfo.getIntValue("baseFare") + cachePriceinfo.getIntValue("tax")) > reqPriceInfo.getBaseFare() + reqPriceInfo.getTax()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
