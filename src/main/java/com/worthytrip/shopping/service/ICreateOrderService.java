package com.worthytrip.shopping.service;

import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.RequestCreateOrderBean;

public interface ICreateOrderService {

    JSONObject createOeder(RequestCreateOrderBean requestbody);

    boolean checkOrderExistence(RequestCreateOrderBean requestbody);

    void keepOrderKeyRedis(String str, RequestCreateOrderBean requestbody);

    String orderRequestStringSplicing(RequestCreateOrderBean requestbody);
}
