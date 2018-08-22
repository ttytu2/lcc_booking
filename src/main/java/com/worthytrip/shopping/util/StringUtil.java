package com.worthytrip.shopping.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.RequestCreateOrderBean;
import com.worthytrip.shopping.service.ICityCodeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类
 */
public class StringUtil {

    /**
     * 校验是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNullOrBlank(String str) {
        return ((str == null) || Constants.STRING_EMPTY.equals(str) || "null".equalsIgnoreCase(str));
    }


    /**
     * 获取哪天后的日期号
     */
    public static String getDateAfterNDay(int n) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + n);
        return new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());

    }


    public static String getNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    /***************************************************************************
     * 获取随机数
     **************************************************************************/
    public static int random(int seed, int min) {
        return (int) (Math.random() * seed) + min;
    }


    /**
     * 获取指定个数的随机码
     *
     * @param num
     * @return
     */
    public static String getRandomCode(int num) {
        String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder code = new StringBuilder(0);
        for (int i = 0; i < num; i++) {
            code.append(String.valueOf(randString.charAt(new Random().nextInt(randString.length()))));
        }
        return code.toString();
    }

    /**
     * 获取JSONObject 对象列表，传入[{},{},{}]
     */
    public static List<JSONObject> getJSONObjectListFromArrayStr(String arrayStr) {
        List<JSONObject> jsonObjectList = new ArrayList<>();
        JSONArray array = JSONArray.parseArray(arrayStr);
        if (array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject job = array.getJSONObject(i);
                jsonObjectList.add(job);
            }
        }
        return jsonObjectList;
    }

    /**
     * 将爬虫抓取的数据价格化为响应要求的格式
     */
    public static String conversionForBeRequiredOnPriceInfo(String beGrapData, ICityCodeService cityCodeService, String ipcc) {
        List<JSONObject> flightObjectList = StringUtil
                .getJSONObjectListFromArrayStr(beGrapData);
        int itineraryId = 0;
        for (JSONObject flight : flightObjectList) {
            flight.put("itineraryId", itineraryId);
            itineraryId++;
            List<JSONObject> priceInfos = null;
            List<JSONObject> frompriceList = StringUtil
                    .getJSONObjectListFromArrayStr(flight.get("fromPriceInfos").toString());
            priceInfos = frompriceList;
            int adt = 0;
            int chd = 0;
            if ("9C_F".equals(ipcc) || "LCJC_F".equals(ipcc)) {
                for (int i = 0; i < frompriceList.size(); i++) {
                    if ("ADT".equals(priceInfos.get(i).getString("passengerType"))) {
                        adt = 1;
                    } else if ("CHD".equals(priceInfos.get(i).getString("passengerType"))) {
                        chd = 1;
                    }
                }
            }

            flight.remove("fromPriceInfos");
            if (flight.containsKey("retPriceInfos")) {
                List<JSONObject> retpriceList = StringUtil
                        .getJSONObjectListFromArrayStr(flight.get("retPriceInfos").toString());
                flight.remove("retPriceInfos");
                for (int i = 0; i < priceInfos.size(); i++) {
                    int tmp = priceInfos.get(i).getIntValue("baseFare");
                    priceInfos.get(i).remove("baseFare");
                    priceInfos.get(i).put("baseFare", tmp + retpriceList.get(i).getIntValue("baseFare"));
                }
            }
            if (!"LCJC_F".equals(ipcc)) {
                for (int i = 0; i < priceInfos.size(); i++) {
                    int tmp = priceInfos.get(i).getIntValue("baseFare");
                    if (tmp > 99) {
                        priceInfos.get(i).put("tax", 99);
                        priceInfos.get(i).remove("baseFare");
                        priceInfos.get(i).put("baseFare", tmp - 99);
                    } else {
                        priceInfos.get(i).put("tax", 1);
                        priceInfos.get(i).remove("baseFare");
                        priceInfos.get(i).put("baseFare", tmp - 1);
                    }

                }
            }

            flight.put("priceInfos", priceInfos);
            //添加fromTo
            flight.remove("fromTo");
            //去程的航班数--增加值baggages
            JSONObject routeCodes = JSONObject.parseObject(flight.getString("routeCodes"));
            int fromFlightNumber = routeCodes.getString("airports").split("_")[0].split(",").length;
            int retFlightNumber = 0;
            if (routeCodes.getString("airports").split("_").length > 1) {
                //返程的航班数
                retFlightNumber = routeCodes.getString("airports").split("_")[1].split(",").length;
            }
            List<JSONObject> baggageList = new ArrayList<>();
            for (int i = 0; i < fromFlightNumber + retFlightNumber; i++) {
                JSONObject baggage = new JSONObject();
                if ("9C_F".equals(ipcc)) {
                    baggage.put("adultBaggage", adt > 0 ? "1P" : "");
                    baggage.put("adultWeight", adt > 0 ? "10KG" : "");
                    baggage.put("childBaggage", chd > 0 ? "1P" : "");
                    baggage.put("childWeight", chd > 0 ? "10KG" : "");
                } else if ("LCJC_F".equals(ipcc)) {
                    baggage.put("adultBaggage", adt > 0 ? "1P" : "");
                    baggage.put("adultWeight", adt > 0 ? "20KG" : "");
                    baggage.put("childBaggage", chd > 0 ? "1P" : "");
                    baggage.put("childWeight", chd > 0 ? "20KG" : "");
                } else {
                    baggage.put("adultBaggage", "0KG");
                    baggage.put("adultWeight", "");
                    baggage.put("childBaggage", "0KG");
                    baggage.put("childWeight", "");
                    baggage.put("enAdultBaggage", "");
                    baggage.put("enChildBaggage", "");
                }
                baggage.put("segmentNo", i + 1);
                baggageList.add(baggage);
            }
            flight.put("baggages", baggageList);
            String airportsPre = routeCodes.getString("airports").split("_")[0];
            String firstCarrier = routeCodes.getString("carriers").split("_")[0].split(",")[0];
            String fromAirport = airportsPre.split(",")[0].split("-")[0];
            String toAirport = airportsPre.split(",")[airportsPre.split(",").length - 1].split("-")[1];
            String fromTo = cityCodeService.getCityCodeFromAirportCode(fromAirport) + "-" + cityCodeService.getCityCodeFromAirportCode(toAirport);
            flight.put("fromTo", fromTo);
            flight.put("ds", "LCC_F");
            flight.put("ipcc", ipcc);
            flight.put("validatingCarrier", firstCarrier);
            //单程时不为空
            if (!flight.containsKey(Constants.RETSEGMENTS)) {
                flight.put(Constants.RETSEGMENTS, "[]");
            }
            /**修改fromSegments中的城市三字码*/
            List<JSONObject> segList = StringUtil
                    .getJSONObjectListFromArrayStr(flight.get("fromSegments").toString());
            for (JSONObject seg : segList) {
                seg.remove("depCity");
                seg.put("depCity", cityCodeService.getCityCodeFromAirportCode(seg.getString("depAirport")));
                seg.remove("arrCity");
                seg.put("arrCity", cityCodeService.getCityCodeFromAirportCode(seg.getString("arrAirport")));
            }
            if (flight.containsKey("retSegments")) {
                List<JSONObject> retSegList = StringUtil
                        .getJSONObjectListFromArrayStr(flight.get("retSegments").toString());
                for (JSONObject seg : retSegList) {
                    seg.remove("depCity");
                    seg.put("depCity", cityCodeService.getCityCodeFromAirportCode(seg.getString("depAirport")));
                    seg.remove("arrCity");
                    seg.put("arrCity", cityCodeService.getCityCodeFromAirportCode(seg.getString("arrAirport")));
                }
                flight.remove("retSegments");
                flight.put("retSegments", retSegList);
            }
            flight.remove("fromSegments");
            flight.put("fromSegments", segList);
        }

        return flightObjectList.toString();
    }

    /**
     * 检验证件是否有效期半年以上
     *
     * @param requestbody
     * @return
     */
    public static boolean checkCardExpireDate(RequestCreateOrderBean requestbody) {

        if (requestbody.getIpcc().endsWith(Constants.IPCCOption.IPCC_9C.getValue())) {

            List<RequestCreateOrderBean.PassengersBean> passengersBeans = requestbody.getPassengers();

            for (RequestCreateOrderBean.PassengersBean passengersBean : passengersBeans) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long day = 0;
                try {
                    day = sdf.parse(passengersBean.getCardExpired()).getTime() - sdf.parse(getNow()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (day / (1000 * 60 * 60 * 24) < 180) {
                    return true;
                }

            }

            return false;
        } else {
            return false;
        }

    }
}
