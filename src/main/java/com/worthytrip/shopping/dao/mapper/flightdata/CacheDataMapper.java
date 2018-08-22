package com.worthytrip.shopping.dao.mapper.flightdata;

import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;
import org.springframework.stereotype.Repository;
/**
 * @author zongzekun on 21/6/2018 9:55 AM.
 * @version 1.0
 * Description:
 */
@Repository
public interface CacheDataMapper {

    /**
     * 保存抓数结果数据
     * @param record
     * @return
     */
    int saveCacheData(CacheDataModel record);

  
}