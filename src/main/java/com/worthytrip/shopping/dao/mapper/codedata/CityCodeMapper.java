package com.worthytrip.shopping.dao.mapper.codedata;

import com.worthytrip.shopping.dao.model.codedata.CityCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zongzekun on 21/6/2018 9:55 AM.
 * @version 1.0
 * Description:
 */
@Repository
public interface CityCodeMapper {

    /**
     * 查询所有城市码
     * @return
     */
    List<CityCode> queryAllCityCode();
}