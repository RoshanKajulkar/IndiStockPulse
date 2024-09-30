package com.example.indistockpulse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_data_1min")
@IdClass(StockDataId.class)
public class StockDataModel {

    @Id
    @Column(name = "current_epoch_time", nullable = false)
    private Integer currentEpochTime;

    @Id
    @Column(name = "symbol_id", nullable = false)
    private Short symbolId;

    @Column(name = "open", nullable = false)
    private Double open;

    @Column(name = "high", nullable = false)
    private Double high;

    @Column(name = "low", nullable = false)
    private Double low;

    @Column(name = "close", nullable = false)
    private Double close;

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

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }
}
