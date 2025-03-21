package com.app.stocktrading.service;


import com.app.stocktrading.entity.Portfolio;
import com.app.stocktrading.entity.Stock;
import com.app.stocktrading.entity.User;
import com.app.stocktrading.repository.PortfolioRepository;
import com.app.stocktrading.repository.StockRepository;
import com.app.stocktrading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    // ✅ Add or update stock quantity in the portfolio
    public String addOrUpdatePortfolio(Long userId, String symbol, int quantityChange) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Stock> stockOpt = stockRepository.findBySymbol(symbol);

        if (userOpt.isEmpty() || stockOpt.isEmpty()) {
            return "User or Stock not found!";
        }

        User user = userOpt.get();
        Stock stock = stockOpt.get();

        Optional<Portfolio> portfolioOpt = portfolioRepository.findByUserIdAndStockId(user.getId(), stock.getId());

        Portfolio portfolio;
        if (portfolioOpt.isPresent()) {
            portfolio = portfolioOpt.get();
            portfolio.setQuantity(portfolio.getQuantity() + quantityChange);
            if (portfolio.getQuantity() <= 0) {
                portfolioRepository.delete(portfolio); // Remove entry if quantity is zero
                return "Portfolio updated: Stock removed";
            }
        } else {
            if (quantityChange <= 0) return "Invalid quantity!";
            portfolio = Portfolio.builder()
                    .user(user)
                    .stock(stock)
                    .quantity(quantityChange)
                    .build();
        }

        portfolioRepository.save(portfolio);
        return "Portfolio updated successfully!";
    }

    // ✅ Get user portfolio
    public List<Portfolio> getUserPortfolio(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    // ✅ Calculate total portfolio value
    public double calculatePortfolioValue(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findByUserId(userId);
        double totalValue = 0;
        for (Portfolio p : portfolios) {
            totalValue += p.getQuantity() * p.getStock().getPrice();
        }
        return totalValue;
    }
}
