package com.worthytrip.shopping.service;

import com.worthytrip.shopping.dao.model.flightdata.FixedFlight;
import com.worthytrip.shopping.dao.model.flightdata.RequstCheckPrice;

import java.text.ParseException;
import java.util.List;

public interface IFixedFlightService {

    boolean checkCache(FixedFlight fixedFlight);

    List<FixedFlight> queryAllFlights();

    boolean isFixedFlightGrabing(FixedFlight fixedFlight);

    String getRedisKey(FixedFlight fixedFlight);

    void setGrabCountAndFlushRedis(FixedFlight fixedFlight);

    void changeFixedFlightGrabStatus(FixedFlight fixedFlight, int status);

    FixedFlight createFixedFlightByEntity(RequstCheckPrice request) throws ParseException;

}
