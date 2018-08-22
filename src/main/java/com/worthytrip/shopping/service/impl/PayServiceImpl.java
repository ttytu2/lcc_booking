package com.worthytrip.shopping.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;
import com.worthytrip.shopping.dao.model.flightdata.OrderPnr;
import com.worthytrip.shopping.dao.model.flightdata.RequestPay;
import com.worthytrip.shopping.service.IPayService;
import com.worthytrip.shopping.service.IRedisService;
import com.worthytrip.shopping.util.Constants;
import com.worthytrip.shopping.util.DateFormat;
import com.worthytrip.shopping.util.RoutingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付校验逻辑
 *
 * @author lishuai
 * @date 18-2-12 下午1:48
 */
@Service
public class PayServiceImpl implements IPayService {

    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private OrderPnrServiceImpl orderPnrServiceImpl;

    @Autowired
    private IRedisService redisService;

    /**
     * @param requestPay 价格校验接口请求数据
     * @return com.alibaba.fastjson.JSONObject
     * @author lishuai
     * @date 18-2-12 下午3:51
     */
    @Override
    public JSONObject checkPay(RequestPay requestPay) {

        String pnr = requestPay.getPnr();
        OrderPnr orderPnr = orderPnrServiceImpl.queryByPnrCode(pnr);
        String redisKey = RoutingUtil.createRedisKeyByEntity(requestPay, orderPnr);
        if (orderPnr == null) {
            logger.info("Pay Error({}):PNR已过期或不存在", redisKey, pnr);

            return new JSONObject().fluentPut("message", "PNR已过期或不存在").fluentPut("status", 0);
        }

        CacheDataModel cacheDataModel = (CacheDataModel) redisService.get(redisKey);

        if (this.isTimeout(orderPnr)) {
            logger.info("Pay Error({}):支付超时---orderPnr:{}", redisKey, orderPnr);

            return new JSONObject().fluentPut("message", "支付超时").fluentPut("status", 0);
        }

        if (cacheDataModel == null) {
            logger.info("Pay Error({}):缓存数据已失效,orderPnr:\n{}", redisKey, JSON.toJSONString(orderPnr, true));

            return new JSONObject().fluentPut("message", "缓存数据已失效").fluentPut("status", 0);
        }

        JSONArray routings = JSON.parseArray(cacheDataModel.getResultJson());
        JSONObject rightRouting = RoutingUtil.getRightRouting(routings, (JSONObject) JSON.toJSON(requestPay.getRouteCodes()), orderPnr.getAdultnumber(), orderPnr.getChildnumber(), requestPay.getIpcc());

        if (rightRouting == null) {
            logger.info("Pay Error({}):未匹配到合适的routing;\n请求体:{};\n缓存源:{}", redisKey, JSON.toJSONString(requestPay, true), JSON.toJSONString(cacheDataModel.getResultJson(), true));

            return new JSONObject().fluentPut("message", "未匹配到正确的routing").fluentPut("status", 0);
        }

        if (this.checkCachePriceCount(routings, requestPay)) {
            logger.info("Pay Error({}):priceInfos参数错误,请求源:\n{},缓存:\n{}",redisKey, JSON.toJSONString(requestPay, true), JSON.toJSONString(cacheDataModel.getResultJson(), true));

            return new JSONObject().fluentPut("message", "参数错误").fluentPut("status", 0);
        }

        if (isChangePrice(requestPay, rightRouting.getJSONArray(Constants.PRICEINFOS))) {
            logger.info("Pay Success({}):价格变动", redisKey);

            return new JSONObject().fluentPut("message", "价格变动,支付失败").fluentPut("status", 0);
        }

        logger.info("Pay Success({}):支付校验成功", redisKey);

        return new JSONObject().fluentPut("message", "success").fluentPut("status", 1);
    }

    /**
     * 检测支付是否超时
     *
     * @author lishuai
     * @date 18-3-1 上午10:39
     */
    private boolean isTimeout(OrderPnr orderPnr) {
        long createTime = DateFormat.str2Long(orderPnr.getCreatetime());
        long nowTime = System.currentTimeMillis();
        return (createTime + Constants.MILLISECOND_OF_TEN_MINUTES) < nowTime;
    }

    /**
     * 检测价格数量是否相同
     *
     * @author lishuai
     * @date 18-3-1 上午10:38
     */
    private boolean checkCachePriceCount(JSONArray routings, RequestPay requestPay) {
        List<RequestPay.PriceInfosBean> reqPriceInfos = requestPay.getPriceInfos();
        OrderPnr orderPnr = orderPnrServiceImpl.queryByPnrCode(requestPay.getPnr());
        JSONObject routing = RoutingUtil.getRightRouting(routings, (JSONObject) JSON.toJSON(requestPay.getRouteCodes()), orderPnr.getAdultnumber(), orderPnr.getChildnumber(), requestPay.getIpcc());
        JSONArray cachePriceInfos = routing.getJSONArray("priceInfos");
        return reqPriceInfos.size() != cachePriceInfos.size();
    }

    /**
     * 检测价格是否变动
     *
     * @author lishuai
     * @date 18-3-1 上午10:37
     */
    private boolean isChangePrice(RequestPay requestPay, JSONArray cachePriceInfos) {
        for (RequestPay.PriceInfosBean reqPriceInfo : requestPay.getPriceInfos()) {
            String reqPassengerType = reqPriceInfo.getPassengerType();
            int reqBaseFare = reqPriceInfo.getBaseFare();
            int reqTax = reqPriceInfo.getTax();
            for (int j = 0; j < cachePriceInfos.size(); j++) {
                String cachePassengerType = cachePriceInfos.getJSONObject(j).getString("passengerType");
                int cacheBaseFare = cachePriceInfos.getJSONObject(j).getIntValue("baseFare");
                int cacheTax = cachePriceInfos.getJSONObject(j).getIntValue("tax");
                if (reqPassengerType.equals(cachePassengerType)) {
                    if (reqBaseFare + reqTax > cacheBaseFare + cacheTax) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
