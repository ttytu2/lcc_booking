package com.worthytrip.shopping.util;

public class Constants {
    /**
     * 价格校验:102
     */
    public static final int TYPE_PRICING = 102;

    /**
     * 生单:103
     */
    public static final int TYPE_BOOKING = 103;
    /**
     * 支付校验:108
     */
    public static final int TYPE_PAYCHECKING = 108;
    /***请求执行id*/
    public static final String TRADEID = "tradeId";
    /***响应jsonKEY */
    public static final String STATUS = "status";// 响应状态值
    public static final String HEADER_CONTENT_TYPE = "Content-type";// 抓取数据请求头Key
    public static final String HEADER_CONTENT_TYPE_VALUE = "application/json";// 请求头值
    public static final String CHARSET = "UTF-8";
    public static final String ROUTINGS = "routings";// 响应中航线结果key
    public static final String FROMSEGMENTS = "fromSegments";// 响应中出发航线key
    public static final String SEATSREMAIN = "seatsRemain";// 响应中每条航线剩余座位数key
    public static final String RETSEGMENTS = "retSegments";// 响应中返回航线key
    public static final String FROMPRICEINFOS = "fromPriceInfos";//爬虫响应中的出发价格信息Key
    public static final String RETPRICEINFOS = "retPriceInfos";//爬虫响应中的出发价格信息Key
    public static final String FLIGHTNUMBER = "flightNumber";//爬虫响应中的航班号信息Key
    public static final String FROMTO = "fromTo";//响应中的必须信息Key
    public static final String ROUTECODES = "routeCodes";//响应中的必须信息Key
    public static final String AIRPORTS = "airports";//响应中的必须信息Key
    public static final String ADULTBAGGAGE = "adultBaggage";//响应中的必须信息Key
    public static final String ADULTWEIGHT = "adultWeight";//响应中的必须信息Key
    public static final String CHILDBAGGAGE = "childBaggage";//响应中的必须信息Key
    public static final String CHILDWEIGHT = "childWeight";//响应中的必须信息Key
    public static final String ENADULTBAGGAGE = "enAdultBaggage";//响应中的必须信息Key
    public static final String ENCHILDBAGGAGE = "enChildBaggage";//响应中的必须信息Key
    public static final String SEGMENTNO = "segmentNo";//响应中的必须信息Key
    public static final String VALIDATINGCARRIER = "validatingCarrier";//响应中的必须信息Key
    /**
     * 响应中价格信息key
     */
    public static final String TAX = "tax";//响应中的价格信息税Key
    public static final String BASEFARE = "baseFare";//响应中的价格信息基本价格Key
    public static final String PRICEINFOS = "priceInfos";//响应中的价格信息Key
    /**
     * 生单响应中必须key
     */
    public static final String CABINS = "cabins";//生单响应中的必须信息Key
    public static final String FLIGHTTIMES = "flightTimes";//生单响应中的必须信息Key
    public static final String LASTTICKETDATE = "lastTicketDate";//生单响应中的必须信息Key
    public static final String PNRCODE = "pnrCode";//生单响应中的必须信息Key
    public static final String PNRID = "pnrId";//生单响应中的必须信息Key
    public static final String SPECIALADULTTYPE = "specialAdultType";//生单响应中的必须信息Key


    /*** 请求jsonKEY */
    public static final String FLIGHTOPTION = "flightOption";// 请求json中行程类型key值
    public static final String FROMCITY = "fromCity";// 请求json中出发城市三字码key值
    public static final String TOCITY = "toCity";// 请求json中目标城市三字码key值
    public static final String STARTDATE = "startDate";// 请求json中出发日期key值
    public static final String RETDATE = "retDate";// 请求json中返回日期key值
    public static final String ADULTNUMBER = "adultNumber";// 请求json中成人数量key值
    public static final String CHILDNUMBER = "childNumber";// 请求json中儿童数量key值
    public static final String CID = "cid";// 请求json中cidkey值
    public static final String CID_VALUE = "cid_ctrip";// 请求json中cid值
    public static final String DS = "ds";// 请求json中dskey值
    public static final String DS_VALUE = "LCC_F";// 请求json中ds值
    public static final String IPCC = "ipcc";// 请求json中ipcckey值
    public static final String PARSER = "parser";// 请求json中parserkey值
    public static final String BROWSER = "browser";// 请求json中browserkey值
    public static final String ENTRY = "entry";// 请求json中entrykey值
    public static final String NORETDATE = "noretDate";// 请求json中单程时retDate的默认值
    public static final String SUCCESS = "success";

    public static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String STRING_EMPTY = "";

    public static final int SECONDS_OF_ONE_HOUR = 60 * 60;

    /**
     * 最大超时次数
     */
    public static final int MAX_TIMEOUT_COUNT = 5;


    /**
     * 十分钟毫秒数
     */
    public static final int MILLISECOND_OF_TEN_MINUTES = 1000 * 60 * 10;

    /**
     * 五分钟毫秒数
     */
    public static final int MILLISECOND_OF_FIVE_MINUTES = 1000 * 60 * 10;

    /**
     * 价格校验合格分数
     */
    public static final byte CHECK_PRICE_PASS_SCORE = 60;

    /**
     * 单程/往返
     */
    public enum FlightOption {
        ONE_WAY(0, "oneWay"),
        ROUND_TRIP(1, "roundTrip");


        private int code;
        private String value;

        FlightOption(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String codeOf(int code) {
            try {
                FlightOption[] flightOptions = values();
                for (FlightOption flightOption : flightOptions) {
                    if (flightOption.getCode() == code) {
                        return flightOption.getValue();
                    }
                }
                throw new RuntimeException("没有该枚举...");
            } catch (Exception e) {
            }
            return null;
        }
    }

    /**
     * 爬虫请求选项
     */
    public enum GrapType {
        QUERY(0, "查询请求"),
        AUTO(1, "自动请求"),
        QUEUE(2, "超时队列请求"),
        ORDER(3, "生单请求"),
        PRICE(4, "验价请求");

        GrapType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private int code;
        private String value;


        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static final String STRING_ONE = "1";
    public static final String STRING_THREE = "3";
    public static final int INT_THREE = 3;
    public static final int INT_ONE = 1;
    public static final String STRING_ZERO = "0";
    public static final int INT_ZERO = 0;

    public static final int INT_TWO = 2;

    public static final String STRING_TWO = "2";

    public static final String STRING_FOUR = "4";

    public static final int INT_FOUR = 4;

    public static final long MILLISECOND_OF_SIXTY_MINUTES = 1000 * 60 * 60;

    public static final String COMMA = ",";


    public static final String CITYCODE_PREFIX = "cityCode_";

    public static final String AIRPORTCODE_PREFIX = "airportCode_";

    public static final String BLANK_ROUTINGS = "[]";

    public static final String REDIS_FLIGHT_GRAB_STATUS = "lcc_fixedflight_grab_status";

    public static final String CHINESE_PATTERN = "[^\u4e00-\u9fa5^0-9^a-z^A-Z]";

    public static final String STRING_NULL = "null";

    public enum IPCCOption {
        IPCC_TGAU(0, "TGAU_F"), IPCC_9C(1, "9C_F"), IPCC_LCPAM(2, "LCPAM_F"), IPCC_LC5J_F(3, "LC5J_F");
        private int code;
        private String value;

        IPCCOption(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum GrabStatusOption {

        GRAB_GRABING(1, "正在爬取");

        private int code;
        private String value;

        GrabStatusOption(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {

            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

}
