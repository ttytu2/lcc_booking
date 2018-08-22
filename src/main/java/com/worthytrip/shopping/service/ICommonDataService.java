package com.worthytrip.shopping.service;

import com.alibaba.fastjson.JSONObject;
import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;

public interface ICommonDataService {
    String grabData(String json, String redisKeySave, CacheDataModel cacheDataModel, String redisKeyBlack,
                    Integer timeOut, int grapType);

    JSONObject queryData(CacheDataModel requestbody);

    int saveCacheData(CacheDataModel cacheData);
}
