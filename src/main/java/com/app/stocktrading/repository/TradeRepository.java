package com.app.stocktrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.stocktrading.entity.Trade;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByUserId(Long userId);
}
