package com.example.indistockpulse.repository;

import com.example.indistockpulse.model.StockDataModel;
import com.example.indistockpulse.model.StockDataId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepository extends JpaRepository<StockDataModel, StockDataId> {
    // You can define custom queries here if needed
}
