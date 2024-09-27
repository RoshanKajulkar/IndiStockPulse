package com.example.indistockpulse.controllers;

import com.example.indistockpulse.model.MarketData;
import com.example.indistockpulse.repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class MarketDataController {

    @Autowired
    private MarketDataRepository marketDataRepository;

    @GetMapping("/api/market-data")
    public List<MarketData> getAllMarketData() {
        return marketDataRepository.findAll();
    }
}