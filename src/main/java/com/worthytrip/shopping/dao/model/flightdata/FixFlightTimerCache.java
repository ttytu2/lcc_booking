package com.worthytrip.shopping.dao.model.flightdata;

import com.google.common.base.Objects;

public class FixFlightTimerCache {
    private FixedFlight fixedFlight;
    private long updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixFlightTimerCache that = (FixFlightTimerCache) o;
        return Objects.equal(fixedFlight, that.fixedFlight);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fixedFlight);
    }

    public FixedFlight getFixedFlight() {
        return fixedFlight;

    }

    public void setFixedFlight(FixedFlight fixedFlight) {
        this.fixedFlight = fixedFlight;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public FixFlightTimerCache() {
    }

    public FixFlightTimerCache(FixedFlight fixedFlight, long updateTime) {
        this.fixedFlight = fixedFlight;


        this.updateTime = updateTime;
    }
}
