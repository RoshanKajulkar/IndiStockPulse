package com.example.indistockpulse.repository;

import com.example.indistockpulse.model.StockDataModel;
import com.example.indistockpulse.model.StockDataId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDataRepository extends JpaRepository<StockDataModel, StockDataId> {
    List<StockDataModel> findBySymbolIdAndCurrentEpochTimeBetween(Short symbolId, Integer startEpoch, Integer endEpoch);

}
