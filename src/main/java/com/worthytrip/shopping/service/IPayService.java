package com.worthytrip.shopping.service;

import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.RequestPay;

/**
 * @author lishuai
 * @date 18-2-28 下午5:58
 */
public interface IPayService {
     JSONObject checkPay(RequestPay requestPay);
}
