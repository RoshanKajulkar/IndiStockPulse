package com.example.indistockpulse.service;

import com.example.indistockpulse.dto.StockDataRequest;
import com.example.indistockpulse.model.StockDataModel;
import com.example.indistockpulse.model.SymbolModel;
import com.example.indistockpulse.repository.StockDataRepository;
import com.example.indistockpulse.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockDataService {

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    // Method to fetch stock data
    public List<List<Object>> getStockData(StockDataRequest request) {

        Short symbolId = symbolRepository.findBySymbol(request.getSymbol())
                .map(SymbolModel::getId)
                .orElseThrow(() -> new RuntimeException("Invalid symbol"));

        // Convert date format if needed
        long rangeFromEpoch = convertToEpoch(request.getRangeFrom());
        long rangeToEpoch = convertToEpoch(request.getRangeTo());

        // Fetch stock data without grouping
        List<StockDataModel> stockDataList = stockDataRepository.findBySymbolIdAndCurrentEpochTimeBetween(symbolId, (int) rangeFromEpoch, (int) rangeToEpoch);

        // Convert data to the required format for response
        return stockDataList.stream().map(data -> List.of(
                (Object) data.getCurrentEpochTime(),
                (Object) data.getOpen(),
                (Object) data.getHigh(),
                (Object) data.getLow(),
                (Object) data.getClose()
        )).collect(Collectors.toList());
    }

    private long convertToEpoch(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }
}
