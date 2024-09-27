package com.example.indistockpulse.repository;

import com.example.indistockpulse.model.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
}