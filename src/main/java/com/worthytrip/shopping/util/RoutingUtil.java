package com.worthytrip.shopping.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 格式化匹配到的routing
 *
 * @author lishuai
 * @date 2017/01/30
 */
@Component
public class RoutingUtil {

    public static JSONObject formatCheckPriceRouting(JSONObject srcRouting) {

        //gds-omg-service-1.9.0增加，omg接口v1.0.12需要传入
        srcRouting.fluentPut("engineId", "1");

        //移出fromTo
        srcRouting.remove("fromTo");

        JSONObject routeCodes = srcRouting.getJSONObject("routeCodes");

        routeCodes.remove("operatingCarriers");

        //获取源数数据fromSegments和retSegments用于拼装flighTimes
        JSONArray srcFromSegments = srcRouting.getJSONArray("fromSegments");

        JSONArray srcRetSegments = srcRouting.getJSONArray("retSegments");

        //循环删除不必要的字段
        for (int i = 0; i < srcFromSegments.size(); i++) {
            delSegmentFlied(srcFromSegments.getJSONObject(i));
        }
        if (srcRetSegments != null) {
            for (int i = 0; i < srcRetSegments.size(); i++) {
                delSegmentFlied(srcRetSegments.getJSONObject(i));
            }
        }

        srcRouting.remove("flightOption");
        srcRouting.remove("flightClassCode");

        if (srcRouting.getJSONArray(Constants.RETSEGMENTS) == null) {
            srcRouting.fluentPut("retSegments", new JSONArray());
        }

        return srcRouting;
    }

    /**
     * 删除segment中不必要的字段
     *
     * @param segment 航段信息
     */
    private static void delSegmentFlied(JSONObject segment) {
        segment.remove("aircraftCode");
        segment.remove("flightTime");
        segment.remove("meal");
        segment.remove("operatingCarrier");
        segment.remove("operatingFlightNo");
        segment.remove("seatsRemain");
        segment.remove("stayTime");
        segment.remove("stopCities");
    }

    /**
     * @param entity 价格校验接口请求源数据
     * @return java.lang.String
     * @author lishuai
     * @date 18-2-12 下午3:18
     */
    public static String createRedisKeyByEntity(RequestCreateOrderBean entity) {
        StringBuffer redisKey = new StringBuffer();
        redisKey.append(entity.getFlightOption());

        redisKey.append("-");

        redisKey.append(entity.getFromCity());

        redisKey.append("-");

        redisKey.append(entity.getToCity());

        redisKey.append("-");

        redisKey.append(entity.getStartTime());

        redisKey.append("-");

        redisKey.append(StringUtils.isBlank(entity.getEndTime()) ? Constants.NORETDATE : entity.getEndTime());

        redisKey.append("-");

        redisKey.append(entity.getAdultNumber());

        redisKey.append("-");

        redisKey.append(entity.getChildNumber());

        redisKey.append("-");

        redisKey.append(entity.getIpcc());

        return redisKey.toString();

    }

    public static String createRedisKeyByEntity(RequstCheckPrice entity) {
        RequstCheckPrice.RouteCodesBean routeCodes = entity.getRouteCodes();
        String aduldNumber = entity.getAdultNumber();
        String childNumber = entity.getChildNumber();
        StringBuilder redisKey = new StringBuilder();
        String[] airports = routeCodes.getAirports().split("_");
        String[] flightTimes = routeCodes.getFlightTimes().split("_");
        if (airports.length == Constants.INT_TWO) {
            redisKey.append("roundTrip");
        } else {
            redisKey.append("oneWay");
        }
        //fromCity
        redisKey.append("-").append(entity.getFromCity());

        //toAirport
        redisKey.append("-").append(entity.getToCity());

        //startflightTime
        redisKey.append("-").append(flightTimes[0].split("[,/\\s]")[0]);

        //endflightTime
        if (flightTimes.length == Constants.INT_TWO) {
            redisKey.append("-").append(flightTimes[1].split("[,/\\s]")[0]);
        } else {
            redisKey.append("-").append(Constants.NORETDATE);
        }
        //儿童数和成人数
        redisKey.append("-").append(aduldNumber).append("-").append(childNumber);

        redisKey.append("-").append(entity.getIpcc());

        return redisKey.toString();

    }

    public static String createRedisKeyByEntity(RequestPay request, OrderPnr orderPnr) {
        RequestPay.RouteCodesBean routeCodes = request.getRouteCodes();
        StringBuilder redisKey = new StringBuilder();
        String[] airports = routeCodes.getAirports().split("_");
        String[] flightTimes = routeCodes.getFlightTimes().split("_");
        if (airports.length == Constants.INT_TWO) {
            redisKey.append("roundTrip");
        } else {
            redisKey.append("oneWay");
        }

        //fromCity
        redisKey.append("-").append(orderPnr.getFromCity());
        //toCity
        redisKey.append("-").append(orderPnr.getToCity());

        //startflightTime
        redisKey.append("-").append(flightTimes[0].split("[,/\\s]")[0]);
        //endflightTime
        if (flightTimes.length == Constants.INT_TWO) {
            redisKey.append("-").append(flightTimes[1].split("[,/\\s]")[0]);
        } else {
            redisKey.append("-").append(Constants.NORETDATE);
        }

        redisKey.append("-").append(orderPnr.getAdultnumber()).append("-").append(orderPnr.getChildnumber());

        redisKey.append("-").append(orderPnr.getIpcc());

        return redisKey.toString();
    }

    public static String createRedisKeyByEntity(CacheDataModel cacheDataModel) {

        StringBuffer redisKey = new StringBuffer();
        redisKey.append(cacheDataModel.getFlightOption());

        redisKey.append("-");

        redisKey.append(cacheDataModel.getFromCity());

        redisKey.append("-");

        redisKey.append(cacheDataModel.getToCity());

        redisKey.append("-");

        redisKey.append(cacheDataModel.getStartDate());

        redisKey.append("-");

        redisKey.append(cacheDataModel.getRetDate());

        redisKey.append("-");

        redisKey.append(cacheDataModel.getAdultNumber());

        redisKey.append("-");

        redisKey.append(cacheDataModel.getChildNumber());

        redisKey.append("-");

        redisKey.append(cacheDataModel.getIpcc());

        return redisKey.toString();
    }

    public static JSONObject getRightRouting(JSONArray routings, JSONObject reqRouteCodes, int adultNumber, int childNumber, String ipcc) {


        //获取请求从参数中flightNumbers数据，用于查找正确的routing

        //航班号
        String[] reqRouteCodesFlightNoArr = reqRouteCodes.getString("flightNumbers").split("[_]");

        String[] reqFromFlightNos = reqRouteCodesFlightNoArr[0].split("[,，]");

        String[] reqRetFlightNos = reqRouteCodesFlightNoArr.length == 2 ? reqRouteCodesFlightNoArr[1].split("[,，]") : new String[0];


        //定义两个Flag，用于标记是否找到合适的routing

        boolean fromFlag = false;
        boolean retFlag = false;

        JSONObject rightRouting = null;

        for (int i = 0; i < routings.size(); i++) {

            //判断每一个routeCodes

            JSONObject routeCodes = routings.getJSONObject(i).getJSONObject("routeCodes");

            //当两个时间数组和请求的时间数组的个数相匹配时，再判断里面每一个时间是否相同


            String[] fromFlightNoArr = routeCodes.getString("flightNumbers").split("[_]");

            String[] fromFlightNos = fromFlightNoArr[0].split("[,，]");

            String[] retFlightNos = fromFlightNoArr.length == 2 ? fromFlightNoArr[1].split("[,，]") : new String[0];


            if (reqFromFlightNos.length == fromFlightNos.length && reqRetFlightNos.length == retFlightNos.length) {

                for (int j = 0; j < reqFromFlightNos.length; j++) {
                    String reqFromFlightNumber = reqFromFlightNos[j];
                    String fromFlightNumber = fromFlightNos[j];
                    if (!(Integer.parseInt(reqFromFlightNumber.substring(2)) == Integer.parseInt(fromFlightNumber.substring(2)))) {
                        break;
                    }
                    if (j == reqFromFlightNos.length - 1) {
                        fromFlag = true;
                    }
                }

                for (int j = 0; j < reqRetFlightNos.length; j++) {
                    String reqRetFlightNumber = reqRetFlightNos[j];
                    String retFlightNumber = retFlightNos[j];
                    if (!(Integer.parseInt(reqRetFlightNumber.substring(2)) == Integer.parseInt(retFlightNumber.substring(2)))) {
                        break;
                    }
                    if (j == reqRetFlightNos.length - 1) {
                        retFlag = true;
                    }
                }

                if (reqRetFlightNos.length == 0) {
                    retFlag = true;
                }

                if (retFlag && fromFlag) {

                    rightRouting = routings.getJSONObject(i);

                    //拼接baggages
                    int segmentsCount = rightRouting.getJSONObject("routeCodes").getString("airports").split("[_,]").length;

                    //成人数量
                    JSONArray baggages = new JSONArray();
                    for (int j = 0; j < segmentsCount; j++) {
                        if ("9C_F".equals(ipcc)) {
                            JSONObject baggage = new JSONObject();
                            if (adultNumber > 0) {
                                baggage.fluentPut("adultBaggage", "1P");
                                baggage.fluentPut("adultWeight", "10KG");
                            }
                            if (childNumber > 0) {
                                baggage.fluentPut("childBaggage", "1P");
                                baggage.fluentPut("childWeight", "10KG");
                            }
                            baggage.fluentPut("segmentNo", String.valueOf(j + 1));
                            baggages.add(baggage);
                        } else if ("LCJC_F".equals(ipcc)) {
                            JSONObject baggage = new JSONObject();
                            if (adultNumber > 0) {
                                baggage.fluentPut("adultBaggage", "1P");
                                baggage.fluentPut("adultWeight", "20KG");
                            }
                            if (childNumber > 0) {
                                baggage.fluentPut("childBaggage", "1P");
                                baggage.fluentPut("childWeight", "20KG");
                            }
                            baggage.fluentPut("segmentNo", String.valueOf(j + 1));
                            baggages.add(baggage);
                        } else {
                            baggages.fluentAdd(new JSONObject().fluentPut("adultBaggage", "0KG").fluentPut("adultWeight", "").fluentPut("childBaggage", "0KG").fluentPut("segmentNo", String.valueOf(j + 1)));
                        }
                    }
                    rightRouting.fluentPut("baggages", baggages);
                    break;
                }
            }
        }
        return rightRouting;
    }

    public static JSONArray filterRoutingsBySeats(JSONArray routings, int seats) {

        JSONArray filteredRoutings = new JSONArray();
        for (int i = 0; i < routings.size(); i++) {

            JSONObject routing = routings.getJSONObject(i);

            JSONArray fromSegements = routing.getJSONArray(Constants.FROMSEGMENTS);

            if (fromSegements.getJSONObject(0).getIntValue(Constants.SEATSREMAIN) < seats) {
                continue;
            }

            JSONArray retSegments = routing.getJSONArray(Constants.RETSEGMENTS);

            if (retSegments != null && retSegments.size() > 0 && retSegments.getJSONObject(0).getIntValue(Constants.SEATSREMAIN) < seats) {
                continue;
            }
            filteredRoutings.add(routing);
        }
        return filteredRoutings;
    }

    public static String createRedisKeyByEntity(FixedFlight fixedFlight, String fromCity, String toCity) {
        StringBuffer redisKey = new StringBuffer();
        redisKey.append(fixedFlight.getFlightoption() == 0 ? "oneWay" : "roundTrip");
        redisKey.append("-").append(fromCity);
        redisKey.append("-").append(toCity);
        String startTime = DateFormat.addDay(fixedFlight.getStartdate());
        String retTime = DateFormat.addDay(fixedFlight.getStartdate() + fixedFlight.getRetdate());
        redisKey.append("-").append(startTime);
        redisKey.append("-").append(fixedFlight.getFlightoption() == 0 ? Constants.NORETDATE : retTime);
        redisKey.append("-").append(fixedFlight.getAdultnumber());
        redisKey.append("-").append(fixedFlight.getChildnumber());
        redisKey.append("-").append(fixedFlight.getIpcc());
        return redisKey.toString();
    }

    public static String createJsonReq(FixedFlight fixedFlight, String fromAirpost, String toAirport) {

        JSONObject grabJson = new JSONObject();
        grabJson.put(Constants.CID, Constants.CID_VALUE);
        grabJson.put(Constants.FROMCITY, fromAirpost);
        grabJson.put(Constants.TOCITY, toAirport);
        grabJson.put(Constants.STARTDATE, DateFormat.addDay(fixedFlight.getStartdate()));
        grabJson.put(Constants.RETDATE, fixedFlight.getRetdate() == 0 ? "" : DateFormat.addDay(fixedFlight.getStartdate() + fixedFlight.getRetdate()));
        grabJson.put(Constants.FLIGHTOPTION, fixedFlight.getFlightoption() == 0 ? Constants.FlightOption.ONE_WAY.getValue() : Constants.FlightOption.ROUND_TRIP.getValue());
        grabJson.put(Constants.ADULTNUMBER, fixedFlight.getAdultnumber());
        grabJson.put(Constants.CHILDNUMBER, fixedFlight.getChildnumber());
        grabJson.put(Constants.DS, Constants.DS_VALUE);
        grabJson.put(Constants.IPCC, fixedFlight.getIpcc());
        grabJson.put(Constants.PARSER, "js");
        grabJson.put(Constants.BROWSER, "Chrome");
        grabJson.put(Constants.ENTRY, "pc");
        return grabJson.toString();
    }

    public static CacheDataModel createCacheDataByFixedFlight(FixedFlight fixedFlight, String startDate, String retDate) {

        CacheDataModel cacheDataModel = new CacheDataModel();
        cacheDataModel.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        String refreshTime = String.valueOf(System.currentTimeMillis());
        cacheDataModel.setAdultNumber(fixedFlight.getAdultnumber());
        cacheDataModel.setChildNumber(fixedFlight.getChildnumber());
        cacheDataModel.setFlightOption(fixedFlight.getFlightoption() == 0 ? Constants.FlightOption.ONE_WAY.getValue() : Constants.FlightOption.ROUND_TRIP.getValue());
        cacheDataModel.setFromCity(fixedFlight.getFromcity());
        cacheDataModel.setRefreshTime(refreshTime);
        cacheDataModel.setRetDate(retDate);
        cacheDataModel.setStartDate(startDate);
        cacheDataModel.setToCity(fixedFlight.getTocity());
        cacheDataModel.setIpcc(fixedFlight.getIpcc());

        return cacheDataModel;
    }

}
