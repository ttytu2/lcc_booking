package com.worthytrip.shopping.service;

import com.worthytrip.shopping.dao.model.flightdata.OutTimeRequestModel;

public interface IBlackListDataService {
    int saveBlackListData(OutTimeRequestModel outTimeRequest);
}
