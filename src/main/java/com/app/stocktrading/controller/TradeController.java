package com.app.stocktrading.controller;



import com.app.stocktrading.entity.Trade;
import com.app.stocktrading.service.TradeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    // ✅ POST - Execute Trade with Session Check
    @PostMapping("/execute")
    public ResponseEntity<String> executeTrade(
            @RequestParam String symbol,
            @RequestParam String action,   // BUY or SELL
            @RequestParam int quantity,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("Unauthorized - Please log in");
        }

        String result = tradeService.executeTrade(userId, symbol, action, quantity);
        return ResponseEntity.ok(result);
    }

    // ✅ GET - Trade History (Optional: Session check here too)
    @GetMapping("/history")
    public ResponseEntity<List<Trade>> getTradeHistory(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        List<Trade> tradeHistory = tradeService.getTradeHistory(userId);
        return ResponseEntity.ok(tradeHistory);
    }
}
