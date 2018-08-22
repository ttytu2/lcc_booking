package com.worthytrip.shopping.service.impl;

import com.worthytrip.shopping.dao.mapper.codedata.CityCodeMapper;
import com.worthytrip.shopping.dao.model.codedata.CityCode;
import com.worthytrip.shopping.service.ICityCodeService;
import com.worthytrip.shopping.service.IRedisService;
import com.worthytrip.shopping.util.Constants;
import com.worthytrip.shopping.util.PrivateData;
import com.worthytrip.shopping.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.worthytrip.shopping.util.Constants.*;

@Service
public class CityCodeServiceImpl implements ICityCodeService {
    @Autowired
    private CityCodeMapper cityCodeMapper;
    @Autowired
    private IRedisService redisService;

    public List<CityCode> queryAllCityCode() {
        return cityCodeMapper.queryAllCityCode();

    }

    /**
     * 通过城市码获取随机机场码
     */
    @Override
    public String getAirportCodeFromCityCode(String cityCode, String ipcc) {
        if (STRING_EMPTY.equals(cityCode) || cityCode == null) {
            return null;
        }
        if (redisService.get(CITYCODE_PREFIX + cityCode) == null) {
            return null;
        }
        if (ipcc.equals(IPCCOption.IPCC_9C.getValue())) {
            return cityCode;
        }
        if (ipcc.equals(IPCCOption.IPCC_LCPAM.getValue())) {
            return cityCode;
        }
        String airport = redisService.get(CITYCODE_PREFIX + cityCode).toString();
        if (!airport.contains(COMMA)) {
            return airport;
        }
        String[] airports = airport.split(COMMA);
        for (String tempAirport : airports) {
            if (ipcc.equals(IPCCOption.IPCC_TGAU.getValue())) {
                if (PrivateData.TGAU_AIRPORTS.contains(tempAirport)) {
                    return tempAirport;
                }
            }
            if (ipcc.equals(IPCCOption.IPCC_LC5J_F.getValue())) {
                if (PrivateData.CEBU_AIRPORTS.contains(tempAirport)) {
                    return tempAirport;
                }
            }
        }
        return airports[StringUtil.random(airports.length, 0)];
    }

    /**
     * 通过机场码获取城市码（拼接fromto）
     */
    @Override
    public String getCityCodeFromAirportCode(String airport) {
        if (STRING_EMPTY.equals(airport) || airport == null) {
            return null;
        } else {
            if (redisService.get(AIRPORTCODE_PREFIX + airport) == null) {
                return null;
            } else {
                return redisService.get(AIRPORTCODE_PREFIX + airport).toString();
            }
        }
    }
}
