package com.app.stocktrading.controller;

import com.app.stocktrading.entity.Portfolio;
import com.app.stocktrading.service.PortfolioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    // ✅ Add or update stock in user's portfolio (Session secured)
    @PostMapping("/update")
    public ResponseEntity<String> updatePortfolio(
            @RequestParam String symbol,
            @RequestParam int quantityChange,
            HttpSession session) {

        // ✅ Session check
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("Unauthorized - Please log in");
        }

        String result = portfolioService.addOrUpdatePortfolio(userId, symbol, quantityChange);
        return ResponseEntity.ok(result);
    }

    // ✅ Get user portfolio (Session secured)
    @GetMapping("/user")
    public ResponseEntity<List<Portfolio>> getUserPortfolio(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        List<Portfolio> portfolioList = portfolioService.getUserPortfolio(userId);
        return ResponseEntity.ok(portfolioList);
    }

    // ✅ Get total portfolio value (Session secured)
    @GetMapping("/value")
    public ResponseEntity<Double> getPortfolioValue(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        double totalValue = portfolioService.calculatePortfolioValue(userId);
        return ResponseEntity.ok(totalValue);
    }
}
