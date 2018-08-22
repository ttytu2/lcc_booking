package com.worthytrip.shopping.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;
import com.worthytrip.shopping.dao.model.flightdata.FixedFlight;
import com.worthytrip.shopping.dao.model.flightdata.RequstCheckPrice;
import com.worthytrip.shopping.execution.GrabHotFlightAsyncTask;
import com.worthytrip.shopping.service.ICheckPriceService;
import com.worthytrip.shopping.service.IFixedFlightService;
import com.worthytrip.shopping.service.IRedisService;
import com.worthytrip.shopping.util.Constants;
import com.worthytrip.shopping.util.RoutingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.Future;

/**
 * 价格校验业务逻辑
 *
 * @author lishuai
 * @date 2017/01/29
 */
@Service
public class CheckPriceServiceImpl implements ICheckPriceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IRedisService redisService;

    @Autowired
    private GrabHotFlightAsyncTask grabHotFlightAsyncTask;

    @Autowired
    private IFixedFlightService fixedFlightService;

    @Override
    public JSONObject checkPrice(RequstCheckPrice request) throws ParseException, InterruptedException {

        //根据request参数拼装redis查询键
        String redisKey = RoutingUtil.createRedisKeyByEntity(request);
        //从redis缓存中查找数据,强转成CacheDataModel
        CacheDataModel cacheDataModel = ((CacheDataModel) redisService.get(redisKey));

        //价格校验时爬取一次,便于长时间生单可行
        //放入线程池
        Future<FixedFlight> future = grabHotFlightAsyncTask.grabHotFlight(this.createFixedFlightModel(request), Constants.GrapType.PRICE.getCode());

        if (cacheDataModel != null) {
            //再转成JSONObject
            JSONObject resultJson = JSONObject.parseObject("{\"" + Constants.ROUTINGS + "\":" + cacheDataModel.getResultJson() + "}");

            //先获取routings
            JSONArray routings = (JSONArray) resultJson.get(Constants.ROUTINGS);

            //调用私有的方法从找到对应的routing
            JSONObject rightRouting = RoutingUtil.getRightRouting(routings, (JSONObject) JSON.toJSON(request.getRouteCodes()), Integer.parseInt(request.getAdultNumber()), Integer.parseInt(request.getChildNumber()), request.getIpcc());

            //判断返回是否成功
            if (rightRouting == null) {
                logger.info("Check-Price-Error({}):Not Found Right Routing,Requst Data:{}Cache Data:{}", redisKey, JSON.toJSONString(request), JSON.toJSONString(routings));
                return new JSONObject().fluentPut("message", "缓存中没有查询到合适的路线数据!").fluentPut("status", 0);
            }
            logger.info("Check-Price-Success:({}):Requst Data:{}Cache Data:{}", redisKey, JSON.toJSONString(request), JSON.toJSONString(rightRouting));
            return new JSONObject().fluentPut("messages", "success").fluentPut("routing", RoutingUtil.formatCheckPriceRouting(rightRouting)).fluentPut("status", 1);
        } else {
            logger.info("Check-Price-Error:({})Not Found Cache Data", redisKey);
            return new JSONObject().fluentPut("message", "缓存中没该航线信息！").fluentPut("status", 0);
        }
    }

    private FixedFlight createFixedFlightModel(RequstCheckPrice requstCheckPrice) throws ParseException {
        return fixedFlightService.createFixedFlightByEntity(requstCheckPrice);
    }

}
