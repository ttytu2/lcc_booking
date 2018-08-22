package com.worthytrip.shopping.dao.mapper.flightdata;

import com.worthytrip.shopping.dao.model.flightdata.OutTimeRequestModel;
import org.springframework.stereotype.Repository;

@Repository
public interface OutTimeRequestMapper {

    int saveBlackListData(OutTimeRequestModel outTimeRequest);

}
