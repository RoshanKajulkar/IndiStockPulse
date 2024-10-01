package com.example.indistockpulse.controller;

import com.example.indistockpulse.dto.SymbolsResponse;
import com.example.indistockpulse.model.SymbolModel;
import com.example.indistockpulse.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("symbols")
public class SymbolController {

    @Autowired
    private SymbolService symbolService;

    @GetMapping
    public SymbolsResponse getAllSymbols() {
        try {
            List<SymbolModel> symbolModels = symbolService.getAllSymbols();
            List<String> symbols = symbolModels.stream()
                    .map(SymbolModel::getSymbol)
                    .collect(Collectors.toList());
            return new SymbolsResponse("ok", symbols);
        } catch(Exception e) {
            e.printStackTrace();
            return new SymbolsResponse("error", List.of());
        }

    }
}
