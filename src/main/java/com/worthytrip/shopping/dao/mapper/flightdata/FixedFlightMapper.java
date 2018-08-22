package com.worthytrip.shopping.dao.mapper.flightdata;

import com.worthytrip.shopping.dao.model.flightdata.FixedFlight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixedFlightMapper {

    /**
     * @param id
     * @return
     */
    FixedFlight selectByPrimaryKey(Integer id);


    List<FixedFlight> queryAllFlights();

    int insert(FixedFlight fixedFlight);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(FixedFlight fixedFlight);

    int updateByPrimaryKeySelective(FixedFlight fixedFlight);

    int updateByPrimaryKey(FixedFlight fixedFlight);
}