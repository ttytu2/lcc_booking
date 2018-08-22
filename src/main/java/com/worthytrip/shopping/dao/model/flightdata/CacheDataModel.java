package com.worthytrip.shopping.dao.model.flightdata;

import java.io.Serializable;

public class CacheDataModel implements Serializable {
    /**
     * 主健id
     */
    private String id;
    /**
     * json结果字符串
     */
    private String resultJson;
    /**
     * 往返类型
     */
    private String flightOption;
    /**
     * 出发城市三字码
     */
    private String fromCity;
    /**
     * 目标城市三字码
     */
    private String toCity;
    /**
     * 出发日期
     */
    private String startDate;
    /**
     * 返回日期
     */
    private String retDate;
    /**
     * 成人数量
     */
    private int adultNumber;
    /**
     * 小孩数量
     */
    private int childNumber;
    /**
     * 刷新时间
     */
    private String refreshTime;
    /**
     * 剩余数量
     */
    private int surplusNumber;
    /**
     * 抓取状态0--成功，之外均失败
     */
    private int grapStatus;
    /**
     * 抓取耗时ms
     */
    private int grapTime;
    /**
     * 抓取类型0--查询，1--固定，2--超时队列,3--生单抓取，4--验价抓取
     */
    private int grapType;

    private String ipcc;

    public String getIpcc() {
        return ipcc;
    }

    public void setIpcc(String ipcc) {
        this.ipcc = ipcc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
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

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }

    public int getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(int adultNumber) {
        this.adultNumber = adultNumber;
    }

    public int getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(int childNumber) {
        this.childNumber = childNumber;
    }

    public String getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(String refreshTime) {
        this.refreshTime = refreshTime;
    }

    public int getSurplusNumber() {
        return surplusNumber;
    }

    public void setSurplusNumber(int surplusNumber) {
        this.surplusNumber = surplusNumber;
    }

    public int getGrapStatus() {
        return grapStatus;
    }

    public void setGrapStatus(int grapStatus) {
        this.grapStatus = grapStatus;
    }

    public int getGrapTime() {
        return grapTime;
    }

    public void setGrapTime(int grapTime) {
        this.grapTime = grapTime;
    }

    public int getGrapType() {
        return grapType;
    }

    public void setGrapType(int grapType) {
        this.grapType = grapType;
    }

}
