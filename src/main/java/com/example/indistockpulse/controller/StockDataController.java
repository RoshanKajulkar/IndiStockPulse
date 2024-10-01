package com.example.indistockpulse.controller;

import com.example.indistockpulse.dto.StockDataRequest;
import com.example.indistockpulse.dto.StockDataResponse;
import com.example.indistockpulse.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

    // Change to @PostMapping since you are using @RequestBody
    @PostMapping
    public StockDataResponse getStockData(@RequestBody StockDataRequest request) {
        try {
            List<List<Object>> candles = stockDataService.getStockData(request);
            return new StockDataResponse("ok", candles);
        } catch (Exception e) {
            return new StockDataResponse("error", List.of());
        }
    }
}
