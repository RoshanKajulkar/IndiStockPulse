package com.example.indistockpulse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "symbols")
public class SymbolModel {

    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "symbol", length = 20, nullable = false)
    private String symbol;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
