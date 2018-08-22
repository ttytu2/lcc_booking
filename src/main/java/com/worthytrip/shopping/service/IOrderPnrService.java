package com.worthytrip.shopping.service;

import com.worthytrip.shopping.dao.model.flightdata.OrderPnr;

public interface IOrderPnrService {

    int saveOrderPnr(OrderPnr orderPnr);

    OrderPnr queryByPnrCode(String pnrCode);

    String getUniquePnrCode();

}
