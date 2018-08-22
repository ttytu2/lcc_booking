package com.worthytrip.shopping.dao.model.flightdata;

import com.google.common.base.Objects;

import java.io.Serializable;

public class OutTimeRequestModel implements Serializable {
    private String id;
    private String requestJson;
    private String blackKey;
    private int timeCount;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OutTimeRequestModel that = (OutTimeRequestModel) o;
        return timeCount == that.timeCount &&
                Objects.equal(id, that.id) &&
                Objects.equal(requestJson, that.requestJson) &&
                Objects.equal(blackKey, that.blackKey) &&
                Objects.equal(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, requestJson, blackKey, timeCount, createTime);
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public String getBlackKey() {
        return blackKey;
    }

    public void setBlackKey(String blackKey) {
        this.blackKey = blackKey;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
