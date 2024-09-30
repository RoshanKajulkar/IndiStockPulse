package com.example.indistockpulse.model;

import java.io.Serializable;
import java.util.Objects;

public class StockDataId implements Serializable {
    private Integer currentEpochTime;
    private Short symbolId;

    public StockDataId() {}

    public StockDataId(Integer currentEpochTime, Short symbolId) {
        this.currentEpochTime = currentEpochTime;
        this.symbolId = symbolId;
    }

    // Getters and Setters
    public Integer getCurrentEpochTime() {
        return currentEpochTime;
    }

    public void setCurrentEpochTime(Integer currentEpochTime) {
        this.currentEpochTime = currentEpochTime;
    }

    public Short getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(Short symbolId) {
        this.symbolId = symbolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockDataId)) return false;
        StockDataId that = (StockDataId) o;
        return Objects.equals(currentEpochTime, that.currentEpochTime) &&
                Objects.equals(symbolId, that.symbolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentEpochTime, symbolId);
    }
}
