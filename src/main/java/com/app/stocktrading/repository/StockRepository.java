package com.app.stocktrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.stocktrading.entity.Stock;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String symbol);
}
