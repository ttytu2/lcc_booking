package com.worthytrip.shopping.service.impl;

import com.worthytrip.shopping.dao.mapper.flightdata.OutTimeRequestMapper;
import com.worthytrip.shopping.dao.model.flightdata.OutTimeRequestModel;
import com.worthytrip.shopping.service.IBlackListDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackListDataServiceImpl implements IBlackListDataService {
    @Autowired
    private OutTimeRequestMapper outTimeRequestMapper;
    @Override
    public int saveBlackListData(OutTimeRequestModel outTimeRequest) {
        return outTimeRequestMapper.saveBlackListData(outTimeRequest);
    }

}
