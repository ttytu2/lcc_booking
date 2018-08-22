package com.worthytrip.shopping.service;

public interface ICityCodeService {
    String getAirportCodeFromCityCode(String cityCode, String ipcc);

    String getCityCodeFromAirportCode(String airport);

}
