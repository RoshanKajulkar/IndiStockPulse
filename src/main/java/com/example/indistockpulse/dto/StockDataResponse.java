package com.example.indistockpulse.dto;

import java.util.List;

public class StockDataResponse {
    private final String s;
    private final List<List<Object>> candles;

    public StockDataResponse(String s, List<List<Object>> candles) {
        this.s = s;
        this.candles = candles;
    }

    public String getS() {
        return s;
    }

    public List<List<Object>> getCandles() {
        return candles;
    }
}
