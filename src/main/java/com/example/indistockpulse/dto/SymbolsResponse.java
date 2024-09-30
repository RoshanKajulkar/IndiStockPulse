package com.example.indistockpulse.dto;

import java.util.List;

public class SymbolsResponse {
    private final String s;
    private final List<String> symbols; // Change this to List<String>

    public SymbolsResponse(String s, List<String> symbols) {
        this.s = s;
        this.symbols = symbols;
    }

    public String getS() {
        return s;
    }

    public List<String> getSymbols() {
        return symbols;
    }
}
