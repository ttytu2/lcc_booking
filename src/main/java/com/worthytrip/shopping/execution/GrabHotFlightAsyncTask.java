package com.worthytrip.shopping.execution;

import com.worthytrip.shopping.dao.model.flightdata.CacheDataModel;
import com.worthytrip.shopping.dao.model.flightdata.FixedFlight;
import com.worthytrip.shopping.service.impl.CityCodeServiceImpl;
import com.worthytrip.shopping.service.impl.CommonDataServiceImpl;
import com.worthytrip.shopping.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;


@Component
public class GrabHotFlightAsyncTask {

    @Autowired
    private CommonDataServiceImpl commonDataServiceImpl;

    @Autowired
    private CityCodeServiceImpl cityCodeServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("myExcutor")
    public Future<FixedFlight> grabHotFlight(FixedFlight fixedFlight,int grabType)  {

        long start = System.currentTimeMillis();

        String startDate = DateFormat.addDay(fixedFlight.getStartdate());

        String retDate = fixedFlight.getFlightoption() == Constants.INT_ONE ? DateFormat.addDay(fixedFlight.getStartdate() + fixedFlight.getRetdate()) : Constants.NORETDATE;

        String fromCity = fixedFlight.getFromcity();

        String toCity = fixedFlight.getTocity();

        String fromAirport = cityCodeServiceImpl.getAirportCodeFromCityCode(fromCity, fixedFlight.getIpcc());

        String toAirport = cityCodeServiceImpl.getAirportCodeFromCityCode(toCity, fixedFlight.getIpcc());

        String reqJson = RoutingUtil.createJsonReq(fixedFlight, fromAirport, toAirport);

        String redisKey = RoutingUtil.createRedisKeyByEntity(fixedFlight, fromCity, toCity);

        CacheDataModel cacheDataModel = RoutingUtil.createCacheDataByFixedFlight(fixedFlight, startDate, retDate);

        String blackRedisKey = fixedFlight.getFromcity() + "-" + fixedFlight.getTocity() + "-" + "black";

        try {
            commonDataServiceImpl.grabData(reqJson, redisKey, cacheDataModel, blackRedisKey, null,grabType);
        } catch (Exception e) {
            logger.warn("{}: Auto Grab Failed Caused :{}", redisKey, e.getMessage());

            return new AsyncResult<FixedFlight>(null);
        }

        long end = System.currentTimeMillis();

        logger.info("{} Finished, Time Elapsed: {} MS.", fixedFlight, end - start);

        return new AsyncResult<FixedFlight>(fixedFlight);
    }
}
