package com.worthytrip.shopping.service.impl;

import com.worthytrip.shopping.dao.mapper.flightdata.FixedFlightMapper;
import com.worthytrip.shopping.dao.model.flightdata.FixedFlight;
import com.worthytrip.shopping.dao.model.flightdata.RequstCheckPrice;
import com.worthytrip.shopping.service.ICityCodeService;
import com.worthytrip.shopping.service.ICommonDataService;
import com.worthytrip.shopping.service.IFixedFlightService;
import com.worthytrip.shopping.service.IRedisService;
import com.worthytrip.shopping.util.Constants;
import com.worthytrip.shopping.util.DateFormat;
import com.worthytrip.shopping.util.PrivateData;
import com.worthytrip.shopping.util.RoutingUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.List;

@Service
public class FixedFlightServiceImpl implements IFixedFlightService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FixedFlightMapper fixedFlightMapper;
    @Autowired
    private ICommonDataService commonDataService;

    @Autowired
    private ICityCodeService cityCodeService;
    @Autowired
    private IRedisService redisService;

    @Override
    public List<FixedFlight> queryAllFlights() {
        return fixedFlightMapper.queryAllFlights();
    }

    @Override
    public boolean checkCache(FixedFlight fixedFlight) {
        return redisService.isExist(this.getRedisKey(fixedFlight));
    }

    @Override
    public boolean isFixedFlightGrabing(FixedFlight fixedFlight) {

        String redisKey = this.getRedisKey(fixedFlight);
        String hashValue = redisService.getHash(Constants.REDIS_FLIGHT_GRAB_STATUS, redisKey);
        return hashValue != null && String.valueOf(Constants.GrabStatusOption.GRAB_GRABING.getCode()).equals(hashValue);

    }

    @Override
    public void changeFixedFlightGrabStatus(FixedFlight fixedFlight, int status) {
        String redisKey = this.getRedisKey(fixedFlight);
        redisService.setHash(Constants.REDIS_FLIGHT_GRAB_STATUS, redisKey, String.valueOf(status));
    }

    @Override
    public synchronized void setGrabCountAndFlushRedis(FixedFlight fixedFlight) {
        int grabCount = PrivateData.NEED_GRAB_CACHE.get(fixedFlight);
        if (grabCount > Constants.INT_ZERO) {
            PrivateData.NEED_GRAB_CACHE.put(fixedFlight, grabCount - 1);
            //判断该fixedFlight生成的所有异步线程是否全部完成,然后将redis中该fixedFlight的状态设为0,并且在Map中删除fixedFlight
            if ((grabCount - 1) <= Constants.INT_ZERO) {
                PrivateData.NEED_GRAB_CACHE.remove(fixedFlight);
                this.changeFixedFlightGrabStatus(fixedFlight, Constants.INT_ZERO);
            }
        }
    }

    @Override
    public String getRedisKey(FixedFlight fixedFlight) {
        String fromAirport = cityCodeService.getAirportCodeFromCityCode(fixedFlight.getFromcity(), fixedFlight.getIpcc());
        String toAirport = cityCodeService.getAirportCodeFromCityCode(fixedFlight.getTocity(), fixedFlight.getIpcc());
        return RoutingUtil.createRedisKeyByEntity(fixedFlight, fromAirport, toAirport);
    }


    @Override
    public FixedFlight createFixedFlightByEntity(RequstCheckPrice request) throws ParseException {
        FixedFlight fixedFlight = new FixedFlight();
        fixedFlight.setFromcity(request.getFromCity());
        fixedFlight.setTocity(request.getToCity());

        int startDay = DateFormat.subDaysFromTimeString(DateFormat.getCurTimeString(), request.getStartTime());
        fixedFlight.setStartdate(startDay);
        if (StringUtils.equals(request.getFlightOption(), Constants.FlightOption.ROUND_TRIP.getValue())) {
            fixedFlight.setFlightoption(1);
            fixedFlight.setRetdate(DateFormat.subDaysFromTimeString(DateFormat.getCurTimeString(), request.getEndTime()) - startDay);
        } else {
            fixedFlight.setFlightoption(0);
            fixedFlight.setRetdate(0);
        }
        fixedFlight.setAdultnumber(Integer.parseInt(request.getAdultNumber()));
        fixedFlight.setChildnumber(Integer.parseInt(request.getChildNumber()));
        fixedFlight.setIpcc(request.getIpcc());


        return fixedFlight;
    }


}
