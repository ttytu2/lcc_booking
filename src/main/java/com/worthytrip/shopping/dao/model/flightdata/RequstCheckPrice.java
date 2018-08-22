package com.worthytrip.shopping.dao.model.flightdata;

import java.util.List;

public class RequstCheckPrice {

    /**
     * accountCode :
     * adultNumber : 1
     * baggages : [{"adultBaggage":"0KG","adultWeight":"","childBaggage":"0KG","segmentNo":"1"},{"adultBaggage":"0KG","adultWeight":"","childBaggage":"0KG","segmentNo":"2"},{"adultBaggage":"0KG","adultWeight":"","childBaggage":"0KG","segmentNo":"3"},{"adultBaggage":"0KG","adultWeight":"","childBaggage":"0KG","segmentNo":"4"}]
     * childNumber : 0
     * childPrice : 0
     * childTax : 0
     * cid : ctripDirectBj20170206
     * ds : OMG_F
     * data :
     * itineraryId :
     * engineId : 1
     * endTime :
     * flightClass : Economy
     * flightOption : oneWay
     * fromCity : SHA
     * fromSegments : [{"aircraftCode":"320","arrAirport":"TSN","arrTime":"2017-05-07 13:35:00","cabin":"Y","carrier":"GS","codeShare":false,"depAirport":"PVG","depTime":"2017-05-07 11:15:00","flightNumber":"GS7882","marketingCarrier":"","marriageGrp":"","operatingCarrier":"","operatingFlightNo":""},{"aircraftCode":"320","arrAirport":"ICN","arrTime":"2017-05-08 10:30:00","cabin":"E","carrier":"GS","codeShare":false,"depAirport":"TSN","depTime":"2017-05-08 08:05:00","flightNumber":"GS7993","marketingCarrier":"","marriageGrp":"","operatingCarrier":"","operatingFlightNo":""}]
     * guid : 190631445683654022
     * ipcc : OMG_F
     * priceInfos : [{"baseFare":153,"currency":"CNY","passengerType":"ADT","rate":1,"tax":506},{"baseFare":0,"currency":"CNY","passengerType":"CHD","rate":1,"tax":0}]
     * retSegments : []
     * routeCodes : {"airports":"PVG-TSN,TSN-ICN","cabins":"Y,E","carriers":"GS,GS","flightNumbers":"GS7882,GS7993","flightTimes":"2017-05-07 11:15:00/2017-05-07 13:35:00,2017-05-08 08:05:00/2017-05-08 10:30:00","marketingCarriers":"GS,GS","operatingCarriers":"GS,GS"}
     * sessionId : N7SU1490631463517
     * specialAdultType : JCB
     * startTime : 2017-05-07
     * validatingCarrier : HX
     * toCity : SEL
     * tradeId : 102
     */

    private String accountCode;
    private String adultNumber;
    private String childNumber;
    private int childPrice;
    private int childTax;
    private String cid;
    private String ds;
    private String data;
    private String itineraryId;
    private int engineId;
    private String endTime;
    private String flightClass;
    private String flightOption;
    private String fromCity;
    private String guid;
    private String ipcc;
    private RouteCodesBean routeCodes;
    private String sessionId;
    private String specialAdultType;
    private String startTime;
    private String validatingCarrier;
    private String toCity;
    private int tradeId;
    private List<BaggagesBean> baggages;
    private List<FromSegmentsBean> fromSegments;
    private List<PriceInfosBean> priceInfos;
    private List<?> retSegments;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(String adultNumber) {
        this.adultNumber = adultNumber;
    }

    public String getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(String childNumber) {
        this.childNumber = childNumber;
    }

    public int getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(int childPrice) {
        this.childPrice = childPrice;
    }

    public int getChildTax() {
        return childTax;
    }

    public void setChildTax(int childTax) {
        this.childTax = childTax;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getFlightOption() {
        return flightOption;
    }

    public void setFlightOption(String flightOption) {
        this.flightOption = flightOption;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getIpcc() {
        return ipcc;
    }

    public void setIpcc(String ipcc) {
        this.ipcc = ipcc;
    }

    public RouteCodesBean getRouteCodes() {
        return routeCodes;
    }

    public void setRouteCodes(RouteCodesBean routeCodes) {
        this.routeCodes = routeCodes;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSpecialAdultType() {
        return specialAdultType;
    }

    public void setSpecialAdultType(String specialAdultType) {
        this.specialAdultType = specialAdultType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getValidatingCarrier() {
        return validatingCarrier;
    }

    public void setValidatingCarrier(String validatingCarrier) {
        this.validatingCarrier = validatingCarrier;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public List<BaggagesBean> getBaggages() {
        return baggages;
    }

    public void setBaggages(List<BaggagesBean> baggages) {
        this.baggages = baggages;
    }

    public List<FromSegmentsBean> getFromSegments() {
        return fromSegments;
    }

    public void setFromSegments(List<FromSegmentsBean> fromSegments) {
        this.fromSegments = fromSegments;
    }

    public List<PriceInfosBean> getPriceInfos() {
        return priceInfos;
    }

    public void setPriceInfos(List<PriceInfosBean> priceInfos) {
        this.priceInfos = priceInfos;
    }

    public List<?> getRetSegments() {
        return retSegments;
    }

    public void setRetSegments(List<?> retSegments) {
        this.retSegments = retSegments;
    }

    public static class RouteCodesBean {
        /**
         * airports : PVG-TSN,TSN-ICN
         * cabins : Y,E
         * carriers : GS,GS
         * flightNumbers : GS7882,GS7993
         * flightTimes : 2017-05-07 11:15:00/2017-05-07 13:35:00,2017-05-08 08:05:00/2017-05-08 10:30:00
         * marketingCarriers : GS,GS
         * operatingCarriers : GS,GS
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

    public static class BaggagesBean {
        /**
         * adultBaggage : 0KG
         * adultWeight :
         * childBaggage : 0KG
         * segmentNo : 1
         */

        private String adultBaggage;
        private String adultWeight;
        private String childBaggage;
        private String segmentNo;

        public String getAdultBaggage() {
            return adultBaggage;
        }

        public void setAdultBaggage(String adultBaggage) {
            this.adultBaggage = adultBaggage;
        }

        public String getAdultWeight() {
            return adultWeight;
        }

        public void setAdultWeight(String adultWeight) {
            this.adultWeight = adultWeight;
        }

        public String getChildBaggage() {
            return childBaggage;
        }

        public void setChildBaggage(String childBaggage) {
            this.childBaggage = childBaggage;
        }

        public String getSegmentNo() {
            return segmentNo;
        }

        public void setSegmentNo(String segmentNo) {
            this.segmentNo = segmentNo;
        }
    }

    public static class FromSegmentsBean {
        /**
         * aircraftCode : 320
         * arrAirport : TSN
         * arrTime : 2017-05-07 13:35:00
         * cabin : Y
         * carrier : GS
         * codeShare : false
         * depAirport : PVG
         * depTime : 2017-05-07 11:15:00
         * flightNumber : GS7882
         * marketingCarrier :
         * marriageGrp :
         * operatingCarrier :
         * operatingFlightNo :
         */

        private String aircraftCode;
        private String arrAirport;
        private String arrTime;
        private String cabin;
        private String carrier;
        private boolean codeShare;
        private String depAirport;
        private String depTime;
        private String flightNumber;
        private String marketingCarrier;
        private String marriageGrp;
        private String operatingCarrier;
        private String operatingFlightNo;

        public String getAircraftCode() {
            return aircraftCode;
        }

        public void setAircraftCode(String aircraftCode) {
            this.aircraftCode = aircraftCode;
        }

        public String getArrAirport() {
            return arrAirport;
        }

        public void setArrAirport(String arrAirport) {
            this.arrAirport = arrAirport;
        }

        public String getArrTime() {
            return arrTime;
        }

        public void setArrTime(String arrTime) {
            this.arrTime = arrTime;
        }

        public String getCabin() {
            return cabin;
        }

        public void setCabin(String cabin) {
            this.cabin = cabin;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public boolean isCodeShare() {
            return codeShare;
        }

        public void setCodeShare(boolean codeShare) {
            this.codeShare = codeShare;
        }

        public String getDepAirport() {
            return depAirport;
        }

        public void setDepAirport(String depAirport) {
            this.depAirport = depAirport;
        }

        public String getDepTime() {
            return depTime;
        }

        public void setDepTime(String depTime) {
            this.depTime = depTime;
        }

        public String getFlightNumber() {
            return flightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
        }

        public String getMarketingCarrier() {
            return marketingCarrier;
        }

        public void setMarketingCarrier(String marketingCarrier) {
            this.marketingCarrier = marketingCarrier;
        }

        public String getMarriageGrp() {
            return marriageGrp;
        }

        public void setMarriageGrp(String marriageGrp) {
            this.marriageGrp = marriageGrp;
        }

        public String getOperatingCarrier() {
            return operatingCarrier;
        }

        public void setOperatingCarrier(String operatingCarrier) {
            this.operatingCarrier = operatingCarrier;
        }

        public String getOperatingFlightNo() {
            return operatingFlightNo;
        }

        public void setOperatingFlightNo(String operatingFlightNo) {
            this.operatingFlightNo = operatingFlightNo;
        }
    }

    public static class PriceInfosBean {
        /**
         * baseFare : 153
         * currency : CNY
         * passengerType : ADT
         * rate : 1
         * tax : 506
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
