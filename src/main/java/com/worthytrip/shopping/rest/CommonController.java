package com.worthytrip.shopping.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;
import com.worthytrip.shopping.dao.model.flightdata.RequestCreateOrderBean;
import com.worthytrip.shopping.dao.model.flightdata.RequestPay;
import com.worthytrip.shopping.dao.model.flightdata.RequstCheckPrice;
import com.worthytrip.shopping.service.*;
import com.worthytrip.shopping.service.impl.PayServiceImpl;
import com.worthytrip.shopping.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;

@Controller
@RequestMapping("/lcc-asia-service")
public class CommonController {
    @Autowired
    private ICommonDataService commonDataService;
    @SuppressWarnings("unused")
    @Autowired
    private IRedisService redisService;
    @Autowired
    private ICityCodeService cityCodeService;

    @Autowired
    private ICreateOrderService createOrderService;
    @Resource
    private ICheckPriceService checkPriceService;

    @Autowired
    private PayServiceImpl payService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 从redis中获取数据,如果没调用爬虫抓取数据
     */

    @RequestMapping(
            value = {"/shopping"},
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8",
            consumes = {
                    MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8",
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.TEXT_XML_VALUE + ";charset=UTF-8",
                    MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"})
    @ResponseBody
    public JSONObject query(@RequestBody String requestJson) {

        JSONObject requestObject = JSON.parseObject(requestJson);
        CacheDataModel cacheData = new CacheDataModel();
        cacheData.setFlightOption(requestObject.getString(Constants.FLIGHTOPTION));
        String fromCityCode = requestObject.getString(Constants.FROMCITY);

        cacheData.setFromCity(fromCityCode);

        String toCityCode = requestObject.getString(Constants.TOCITY);

        cacheData.setToCity(toCityCode);

        cacheData.setStartDate(requestObject.getString(Constants.STARTDATE));

        cacheData.setIpcc(requestObject.getString(Constants.IPCC));

        if (Constants.FlightOption.ROUND_TRIP.getValue().equals(requestObject.getString(Constants.FLIGHTOPTION))) {
            cacheData.setRetDate(requestObject.getString(Constants.RETDATE));
        } else {
            cacheData.setRetDate(Constants.NORETDATE);

        }
        cacheData.setAdultNumber(requestObject.getIntValue(Constants.ADULTNUMBER));
        cacheData.setChildNumber(requestObject.getIntValue(Constants.CHILDNUMBER));
        JSONObject result = commonDataService.queryData(cacheData);

        // 统计返回的结果数量
        int routingsCount;
        try {
            routingsCount = result.getJSONArray("routings").size();
        } catch (Exception e) {
            logger.info("Not Found Routings or Grab Data Failed");
            routingsCount = 0;
        }

        logger.info("Req Query------IPCC:{};{}-{} {}/{} Adult:{} Child:{} LineCount:{}", cacheData.getIpcc(), fromCityCode, toCityCode, cacheData
                        .getStartDate(), cacheData.getRetDate(), cacheData.getAdultNumber(), cacheData.getChildNumber(),
                routingsCount);
        return result;
    }

    /**
     * 价格校验或者生单或支付接口
     */
    @RequestMapping(
            value = {"/booking"},
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8",
            consumes = {
                    MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8",
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.TEXT_XML_VALUE + ";charset=UTF-8",
                    MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"})
    @ResponseBody
    public JSONObject priceCheckANDcreateOrder(@RequestBody String requestJson) throws ParseException, InterruptedException {

        JSONObject requestObject = JSON.parseObject(requestJson);
        JSONObject result = new JSONObject();
        if (requestObject.getIntValue(Constants.TRADEID) == Constants.TYPE_PRICING) {
            RequstCheckPrice requestBody = JSON.parseObject(requestJson, RequstCheckPrice.class);
            result = checkPriceService.checkPrice(requestBody);
        }

        if (requestObject.getIntValue(Constants.TRADEID) == Constants.TYPE_BOOKING) {
            RequestCreateOrderBean requestbody = JSON.toJavaObject(JSON.parseObject(requestJson),
                    RequestCreateOrderBean.class);

            result = createOrderService.createOeder(requestbody);
            if (result.getIntValue(Constants.STATUS) == Constants.INT_ONE) {
                String key = createOrderService.orderRequestStringSplicing(requestbody);
                createOrderService.keepOrderKeyRedis(key, requestbody);
            }

        }

        if (requestObject.getIntValue(Constants.TRADEID) == Constants.TYPE_PAYCHECKING) {
            RequestPay requestPay = JSON.toJavaObject(JSON.parseObject(requestJson), (RequestPay.class));
            result = payService.checkPay(requestPay);
        }
        return result;
    }
}
