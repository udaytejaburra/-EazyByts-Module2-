package com.app.stocktrading.service;

import com.app.stocktrading.entity.Stock;
import com.app.stocktrading.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // Fetch all available stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // Fetch stock by symbol
    public Optional<Stock> getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    // Add or update a stock
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    // Update stock price
    public Optional<Stock> updateStockPrice(String symbol, Double newPrice) {
        Optional<Stock> stockOpt = stockRepository.findBySymbol(symbol);
        stockOpt.ifPresent(stock -> {
            stock.setPrice(newPrice);
            stockRepository.save(stock);
        });
        return stockOpt;
    }
}
