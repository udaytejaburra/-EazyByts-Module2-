package com.app.stocktrading.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.app.stocktrading.entity.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    // Find all stocks a user owns
    List<Portfolio> findByUserId(Long userId);

    // Find if the user owns a specific stock
    Optional<Portfolio> findByUserIdAndStockId(Long userId, Long stockId);
}
