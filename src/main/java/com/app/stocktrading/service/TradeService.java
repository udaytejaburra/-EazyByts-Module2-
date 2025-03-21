package com.app.stocktrading.service;

import com.app.stocktrading.entity.Stock;
import com.app.stocktrading.entity.Trade;
import com.app.stocktrading.entity.User;
import com.app.stocktrading.repository.StockRepository;
import com.app.stocktrading.repository.TradeRepository;
import com.app.stocktrading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    // ✅ Buy or Sell operation
    public String executeTrade(Long userId, String symbol, String action, int quantity) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Stock> stockOpt = stockRepository.findBySymbol(symbol);

        if (userOpt.isEmpty() || stockOpt.isEmpty()) {
            return "User or Stock not found";
        }

        User user = userOpt.get();
        Stock stock = stockOpt.get();
        double tradeAmount = stock.getPrice() * quantity;

        if (action.equalsIgnoreCase("BUY")) {
            if (user.getBalance() < tradeAmount) {
                return "Insufficient balance!";
            }
            // Deduct balance
            user.setBalance(user.getBalance() - tradeAmount);
        } else if (action.equalsIgnoreCase("SELL")) {
            // In real-world, you would check if user owns the stock (Portfolio)
            // For now, we skip portfolio validation
            user.setBalance(user.getBalance() + tradeAmount);
        } else {
            return "Invalid action! Use BUY or SELL";
        }

        userRepository.save(user); // Update balance

        // Save the trade record
        Trade trade = Trade.builder()
                .user(user)
                .stock(stock)
                .action(action.toUpperCase())
                .quantity(quantity)
                .price(stock.getPrice())
                .tradeTime(LocalDateTime.now())
                .build();

        tradeRepository.save(trade);

        return action.toUpperCase() + " Trade Executed Successfully!";
    }

    // ✅ Get trade history for a user
    public List<Trade> getTradeHistory(Long userId) {
        return tradeRepository.findByUserId(userId);
    }
}
