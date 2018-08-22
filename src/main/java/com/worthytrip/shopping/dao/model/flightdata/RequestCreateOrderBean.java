package com.worthytrip.shopping.dao.model.flightdata;

import java.util.List;

public class RequestCreateOrderBean {


    /**
     * accountCode :
     * adultNumber : 1
     * adultPrice : 4560
     * adultTax : 4560
     * childNumber : 0
     * childPrice : 0
     * childTax : 0
     * cid : ctripDirectBj20170206
     * ds : OMG_F
     * data :
     * endTime : 2017-08-29
     * flightClassCode : E
     * flightOption : roundTrip
     * fromCity : BOS
     * fareType : public
     * fromSegments : [{"aircraftCode":"","arrAirport":"ZRH","arrTime":"2017-06-09 11:00:00","cabin":"Q","carrier":"LX","codeShare":false,"depAirport":"BOS","depTime":"2017-06-08 21:45:00","flightNumber":"LX53","marriageGrp":"","marketingCarrier":"LX","operatingCarrier":"LX","stopAirports":"","stopCities":""},{"aircraftCode":"","arrAirport":"PEK","arrTime":"2017-06-10 05:15:00","cabin":"Q","carrier":"LX","codeShare":false,"depAirport":"ZRH","depTime":"2017-06-09 13:15:00","flightNumber":"LX196","marriageGrp":"","marketingCarrier":"LX","operatingCarrier":"LX","stopAirports":"","stopCities":""}]
     * itineraryId :
     * engineId : 1
     * policyCashBack : 5.00
     * ipcc : OMG_F
     * marriageGrps :
     * passengers : [{"ageType":0,"birthday":"1999-03-09","cardExpired":"2025-05-25","cardIssuePlace":"CN","cardNum":"E51837313","cardType":"PP","firstName":"SHIWEI","gender":"M","lastName":"ZHANG","mobile":"13211111111","name":"ZHANG/SHIWEI","nationality":"CN"}]
     * priceInfos : [{"baseFare":4561,"currency":"CNY","passengerType":"ADT","tax":4907}]
     * retSegments : [{"aircraftCode":"","arrAirport":"ZRH","arrTime":"2017-08-29 11:20:00","cabin":"K","carrier":"LX","codeShare":false,"depAirport":"PEK","depTime":"2017-08-29 06:45:00","flightNumber":"LX197","marriageGrp":"","marketingCarrier":"LX","operatingCarrier":"LX","stopAirports":"","stopCities":""},{"aircraftCode":"","arrAirport":"BOS","arrTime":"2017-08-29 15:20:00","cabin":"K","carrier":"LX","codeShare":false,"depAirport":"ZRH","depTime":"2017-08-29 13:00:00","flightNumber":"LX54","marriageGrp":"","marketingCarrier":"LX","operatingCarrier":"LX","stopAirports":"","stopCities":""}]
     * sessionId : AF1B1490632155495
     * specialAdultType : JCB
     * validatingCarrier : HX
     * startTime : 2017-06-08
     * toCity : BJS
     * tradeId : 103
     */

    private String accountCode;
    private String adultNumber;
    private int adultPrice;
    private int adultTax;
    private String childNumber;
    private int childPrice;
    private int childTax;
    private String cid;
    private String ds;
    private String data;
    private String endTime;
    private String flightClassCode;
    private String flightOption;
    private String fromCity;
    private String fareType;
    private String itineraryId;
    private int engineId;
    private String policyCashBack;
    private String ipcc;
    private String marriageGrps;
    private String sessionId;
    private String specialAdultType;
    private String validatingCarrier;
    private String startTime;
    private String toCity;
    private int tradeId;
    private List<FromSegmentsBean> fromSegments;
    private List<PassengersBean> passengers;
    private List<PriceInfosBean> priceInfos;
    private List<RetSegmentsBean> retSegments;

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

    public int getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }

    public int getAdultTax() {
        return adultTax;
    }

    public void setAdultTax(int adultTax) {
        this.adultTax = adultTax;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFlightClassCode() {
        return flightClassCode;
    }

    public void setFlightClassCode(String flightClassCode) {
        this.flightClassCode = flightClassCode;
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

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
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

    public String getPolicyCashBack() {
        return policyCashBack;
    }

    public void setPolicyCashBack(String policyCashBack) {
        this.policyCashBack = policyCashBack;
    }

    public String getIpcc() {
        return ipcc;
    }

    public void setIpcc(String ipcc) {
        this.ipcc = ipcc;
    }

    public String getMarriageGrps() {
        return marriageGrps;
    }

    public void setMarriageGrps(String marriageGrps) {
        this.marriageGrps = marriageGrps;
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

    public String getValidatingCarrier() {
        return validatingCarrier;
    }

    public void setValidatingCarrier(String validatingCarrier) {
        this.validatingCarrier = validatingCarrier;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public List<FromSegmentsBean> getFromSegments() {
        return fromSegments;
    }

    public void setFromSegments(List<FromSegmentsBean> fromSegments) {
        this.fromSegments = fromSegments;
    }

    public List<PassengersBean> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengersBean> passengers) {
        this.passengers = passengers;
    }

    public List<PriceInfosBean> getPriceInfos() {
        return priceInfos;
    }

    public void setPriceInfos(List<PriceInfosBean> priceInfos) {
        this.priceInfos = priceInfos;
    }

    public List<RetSegmentsBean> getRetSegments() {
        return retSegments;
    }

    public void setRetSegments(List<RetSegmentsBean> retSegments) {
        this.retSegments = retSegments;
    }

    public static class FromSegmentsBean {
        /**
         * aircraftCode :
         * arrAirport : ZRH
         * arrTime : 2017-06-09 11:00:00
         * cabin : Q
         * carrier : LX
         * codeShare : false
         * depAirport : BOS
         * depTime : 2017-06-08 21:45:00
         * flightNumber : LX53
         * marriageGrp :
         * marketingCarrier : LX
         * operatingCarrier : LX
         * stopAirports :
         * stopCities :
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
        private String marriageGrp;
        private String marketingCarrier;
        private String operatingCarrier;
        private String stopAirports;
        private String stopCities;

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

        public String getMarriageGrp() {
            return marriageGrp;
        }

        public void setMarriageGrp(String marriageGrp) {
            this.marriageGrp = marriageGrp;
        }

        public String getMarketingCarrier() {
            return marketingCarrier;
        }

        public void setMarketingCarrier(String marketingCarrier) {
            this.marketingCarrier = marketingCarrier;
        }

        public String getOperatingCarrier() {
            return operatingCarrier;
        }

        public void setOperatingCarrier(String operatingCarrier) {
            this.operatingCarrier = operatingCarrier;
        }

        public String getStopAirports() {
            return stopAirports;
        }

        public void setStopAirports(String stopAirports) {
            this.stopAirports = stopAirports;
        }

        public String getStopCities() {
            return stopCities;
        }

        public void setStopCities(String stopCities) {
            this.stopCities = stopCities;
        }
    }

    public static class PassengersBean {
        /**
         * ageType : 0
         * birthday : 1999-03-09
         * cardExpired : 2025-05-25
         * cardIssuePlace : CN
         * cardNum : E51837313
         * cardType : PP
         * firstName : SHIWEI
         * gender : M
         * lastName : ZHANG
         * mobile : 13211111111
         * name : ZHANG/SHIWEI
         * nationality : CN
         */

        private int ageType;
        private String birthday;
        private String cardExpired;
        private String cardIssuePlace;
        private String cardNum;
        private String cardType;
        private String firstName;
        private String gender;
        private String lastName;
        private String mobile;
        private String name;
        private String nationality;

        public int getAgeType() {
            return ageType;
        }

        public void setAgeType(int ageType) {
            this.ageType = ageType;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCardExpired() {
            return cardExpired;
        }

        public void setCardExpired(String cardExpired) {
            this.cardExpired = cardExpired;
        }

        public String getCardIssuePlace() {
            return cardIssuePlace;
        }

        public void setCardIssuePlace(String cardIssuePlace) {
            this.cardIssuePlace = cardIssuePlace;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }
    }

    public static class PriceInfosBean {
        /**
         * baseFare : 4561
         * currency : CNY
         * passengerType : ADT
         * tax : 4907
         */

        private int baseFare;
        private String currency;
        private String passengerType;
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

        public int getTax() {
            return tax;
        }

        public void setTax(int tax) {
            this.tax = tax;
        }
    }

    public  static class RetSegmentsBean {
        /**
         * aircraftCode :
         * arrAirport : ZRH
         * arrTime : 2017-08-29 11:20:00
         * cabin : K
         * carrier : LX
         * codeShare : false
         * depAirport : PEK
         * depTime : 2017-08-29 06:45:00
         * flightNumber : LX197
         * marriageGrp :
         * marketingCarrier : LX
         * operatingCarrier : LX
         * stopAirports :
         * stopCities :
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
        private String marriageGrp;
        private String marketingCarrier;
        private String operatingCarrier;
        private String stopAirports;
        private String stopCities;

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

        public String getMarriageGrp() {
            return marriageGrp;
        }

        public void setMarriageGrp(String marriageGrp) {
            this.marriageGrp = marriageGrp;
        }

        public String getMarketingCarrier() {
            return marketingCarrier;
        }

        public void setMarketingCarrier(String marketingCarrier) {
            this.marketingCarrier = marketingCarrier;
        }

        public String getOperatingCarrier() {
            return operatingCarrier;
        }

        public void setOperatingCarrier(String operatingCarrier) {
            this.operatingCarrier = operatingCarrier;
        }

        public String getStopAirports() {
            return stopAirports;
        }

        public void setStopAirports(String stopAirports) {
            this.stopAirports = stopAirports;
        }

        public String getStopCities() {
            return stopCities;
        }

        public void setStopCities(String stopCities) {
            this.stopCities = stopCities;
        }
    }
}
