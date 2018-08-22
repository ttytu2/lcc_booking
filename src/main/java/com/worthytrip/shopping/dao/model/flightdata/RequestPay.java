package com.worthytrip.shopping.dao.model.flightdata;

import java.util.List;

public class RequestPay {

    /**
     * adultNumber : 1
     * childNumber : 1
     * adultType : ADT
     * cid : taobaoDirectBj170116
     * currencyCode : CNY
     * ds : OMG_F
     * ipcc : OMG_F
     * lastTicktDate :
     * needverifyprice : 0
     * passengersNumber : 1
     * pnr : OJV9BS
     * pnrId : 1216295
     * fareType : public
     * validatingCarrier : HX
     * priceInfos : [{"baseFare":653,"currency":"CNY","passengerType":"ADT","rate":1,"tax":2511}]
     * routeCodes : {"airports":"CSX-PVG,PVG-AKL_AKL-PVG,SHA-CSX","cabins":"B,T_T,B","carriers":"MU,MU_MU,MU","flightNumbers":"MU9398,MU779_MU780,MU9391","flightTimes":"2017-05-27 19:15:00/2017-05-27 21:15:00,2017-05-28 01:15:00/2017-05-28 16:55:00_2017-06-04 21:00:00/2017-06-05 05:40:00,2017-06-05 10:35:00/2017-06-05 12:35:00","marketingCarriers":"MU,MU_MU,MU","operatingCarriers":"FM,MU_MU,FM"}
     * segmentNumber : 4
     * sessionId : YWUS1490629579846
     * tradeId : 108
     */


    private String adultType;
    private String cid;
    private String currencyCode;
    private String ds;
    private String ipcc;
    private String lastTicktDate;
    private String needverifyprice;
    private String passengersNumber;
    private String pnr;
    private String pnrId;
    private String fareType;
    private String validatingCarrier;
    private RouteCodesBean routeCodes;
    private String segmentNumber;
    private String sessionId;
    private int tradeId;
    private List<PriceInfosBean> priceInfos;



    public String getAdultType() {
        return adultType;
    }

    public void setAdultType(String adultType) {
        this.adultType = adultType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getIpcc() {
        return ipcc;
    }

    public void setIpcc(String ipcc) {
        this.ipcc = ipcc;
    }

    public String getLastTicktDate() {
        return lastTicktDate;
    }

    public void setLastTicktDate(String lastTicktDate) {
        this.lastTicktDate = lastTicktDate;
    }

    public String getNeedverifyprice() {
        return needverifyprice;
    }

    public void setNeedverifyprice(String needverifyprice) {
        this.needverifyprice = needverifyprice;
    }

    public String getPassengersNumber() {
        return passengersNumber;
    }

    public void setPassengersNumber(String passengersNumber) {
        this.passengersNumber = passengersNumber;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getPnrId() {
        return pnrId;
    }

    public void setPnrId(String pnrId) {
        this.pnrId = pnrId;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getValidatingCarrier() {
        return validatingCarrier;
    }

    public void setValidatingCarrier(String validatingCarrier) {
        this.validatingCarrier = validatingCarrier;
    }

    public RouteCodesBean getRouteCodes() {
        return routeCodes;
    }

    public void setRouteCodes(RouteCodesBean routeCodes) {
        this.routeCodes = routeCodes;
    }

    public String getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public List<PriceInfosBean> getPriceInfos() {
        return priceInfos;
    }

    public void setPriceInfos(List<PriceInfosBean> priceInfos) {
        this.priceInfos = priceInfos;
    }

    public static class RouteCodesBean {
        /**
         * airports : CSX-PVG,PVG-AKL_AKL-PVG,SHA-CSX
         * cabins : B,T_T,B
         * carriers : MU,MU_MU,MU
         * flightNumbers : MU9398,MU779_MU780,MU9391
         * flightTimes : 2017-05-27 19:15:00/2017-05-27 21:15:00,2017-05-28 01:15:00/2017-05-28 16:55:00_2017-06-04 21:00:00/2017-06-05 05:40:00,2017-06-05 10:35:00/2017-06-05 12:35:00
         * marketingCarriers : MU,MU_MU,MU
         * operatingCarriers : FM,MU_MU,FM
         */

        private String airports;
        private String cabins;
        private String carriers;
        private String flightNumbers;
        private String flightTimes;
        private String marketingCarriers;
        private String operatingCarriers;

        public String getAirports() {
            return airports;
        }

        public void setAirports(String airports) {
            this.airports = airports;
        }

        public String getCabins() {
            return cabins;
        }

        public void setCabins(String cabins) {
            this.cabins = cabins;
        }

        public String getCarriers() {
            return carriers;
        }

        public void setCarriers(String carriers) {
            this.carriers = carriers;
        }

        public String getFlightNumbers() {
            return flightNumbers;
        }

        public void setFlightNumbers(String flightNumbers) {
            this.flightNumbers = flightNumbers;
        }

        public String getFlightTimes() {
            return flightTimes;
        }

        public void setFlightTimes(String flightTimes) {
            this.flightTimes = flightTimes;
        }

        public String getMarketingCarriers() {
            return marketingCarriers;
        }

        public void setMarketingCarriers(String marketingCarriers) {
            this.marketingCarriers = marketingCarriers;
        }

        public String getOperatingCarriers() {
            return operatingCarriers;
        }

        public void setOperatingCarriers(String operatingCarriers) {
            this.operatingCarriers = operatingCarriers;
        }
    }

    public static class PriceInfosBean {
        /**
         * baseFare : 653
         * currency : CNY
         * passengerType : ADT
         * rate : 1
         * tax : 2511
         */

        private int baseFare;
        private String currency;
        private String passengerType;
        private int rate;
        private int tax;

        public int getBaseFare() {
            return baseFare;
        }

        public void setBaseFare(int baseFare) {
            this.baseFare = baseFare;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPassengerType() {
            return passengerType;
        }

        public void setPassengerType(String passengerType) {
            this.passengerType = passengerType;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public int getTax() {
            return tax;
        }

        public void setTax(int tax) {
            this.tax = tax;
        }
    }
}
