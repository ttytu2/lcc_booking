package com.worthytrip.shopping.service.impl;

import com.worthytrip.shopping.dao.mapper.flightdata.OrderPnrMapper;
import com.worthytrip.shopping.dao.model.flightdata.OrderPnr;
import com.worthytrip.shopping.service.IOrderPnrService;
import com.worthytrip.shopping.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPnrServiceImpl implements IOrderPnrService {
    @Autowired
    private OrderPnrMapper orderPnrMapper;

    @Override
    public int saveOrderPnr(OrderPnr orderPnr) {
        return orderPnrMapper.saveOrderPnr(orderPnr);
    }

    @Override
    public OrderPnr queryByPnrCode(String pnrCode) {
        return orderPnrMapper.queryByPnrCode(pnrCode);
    }

    @Override
    public String getUniquePnrCode() {
        String pnrCode;
        boolean flag = true;
        do {
            pnrCode = StringUtil.getRandomCode(6);
            OrderPnr orderPnr = this.queryByPnrCode(pnrCode);
            if (orderPnr == null) {
                flag = false;
            }
        } while (flag);
        return pnrCode;
    }
}
