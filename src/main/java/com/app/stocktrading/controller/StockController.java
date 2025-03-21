package com.app.stocktrading.controller;

import com.app.stocktrading.entity.Stock;
import com.app.stocktrading.service.StockService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // ✅ GET all stocks (Requires login session)
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();  // Not logged in
        }
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    // ✅ GET stock by symbol (Optional: can protect if needed)
    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStockBySymbol(@PathVariable String symbol, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        Optional<Stock> stock = stockService.getStockBySymbol(symbol);
        return stock.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST - Add a new stock (Admin-only in future)
    @PostMapping
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        Stock savedStock = stockService.saveStock(stock);
        return ResponseEntity.ok(savedStock);
    }

    // ✅ PUT - Update stock price (Optional: make admin-only)
    @PutMapping("/{symbol}/price")
    public ResponseEntity<Stock> updateStockPrice(
            @PathVariable String symbol,
            @RequestParam Double newPrice,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<Stock> updatedStock = stockService.updateStockPrice(symbol, newPrice);
        return updatedStock.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
}
