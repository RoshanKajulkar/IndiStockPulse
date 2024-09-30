package com.example.indistockpulse.service;

import com.example.indistockpulse.model.SymbolModel;
import com.example.indistockpulse.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymbolService {

    @Autowired
    private SymbolRepository symbolRepository;

    // Method to get all symbols
    public List<SymbolModel> getAllSymbols() {
        return symbolRepository.findAll();
    }
}
