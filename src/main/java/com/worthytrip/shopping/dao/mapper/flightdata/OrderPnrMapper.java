package com.worthytrip.shopping.dao.mapper.flightdata;

import com.worthytrip.shopping.dao.model.flightdata.OrderPnr;
import org.springframework.stereotype.Repository;

/**
 * @author lishuai on 21/6/2018 9:55 AM.
 * @version 1.0
 * Description:
 */
@Repository
public interface OrderPnrMapper {

    /**
     *
     * @param orderPnr
     * @return
     */
    int saveOrderPnr(OrderPnr orderPnr);

    OrderPnr queryByPnrCode(String pnrCode);
}