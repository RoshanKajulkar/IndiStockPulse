package com.example.indistockpulse.dto;

import java.util.List;

public class SymbolsResponse {
    private final String s;
    private final List<String> symbols;
    private final int count;

    public SymbolsResponse(String s, List<String> symbols) {
        this.s = s;
        this.symbols = symbols;
        this.count = symbols.size();
    }

    public String getS() {
        return s;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public int getCount() {
        return count;
    }
}
