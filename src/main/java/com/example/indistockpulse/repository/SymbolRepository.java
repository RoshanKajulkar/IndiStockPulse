package com.example.indistockpulse.repository;

import com.example.indistockpulse.model.SymbolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends JpaRepository<SymbolModel, Short> {
    // Custom queries can be defined here if needed
}
