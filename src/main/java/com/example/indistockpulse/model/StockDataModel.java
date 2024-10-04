package com.example.indistockpulse.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_data_15min")
@IdClass(StockDataId.class)
public class StockDataModel {

    @Id
    @Column(name = "current_epoch_time", nullable = false)
    private Integer currentEpochTime;

    @Id
    @Column(name = "symbol_id", nullable = false)
    private Short symbolId;

    @Column(name = "open", nullable = false)
    private BigDecimal open; // Decimal to BigDecimal

    @Column(name = "high", nullable = false)
    private BigDecimal high; // Decimal to BigDecimal

    @Column(name = "low", nullable = false)
    private BigDecimal low; // Decimal to BigDecimal

    @Column(name = "close", nullable = false)
    private BigDecimal close; // Decimal to BigDecimal

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

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }
}
