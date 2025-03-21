package com.app.stocktrading.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Buyer (User who performed the trade)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Stock involved in the trade
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private String action; // BUY or SELL

    @Column(nullable = false)
    private int quantity;  // Number of shares

    @Column(nullable = false)
    private double price;  // Price per share during trade

    @Column(nullable = false)
    private LocalDateTime tradeTime; // Timestamp of the trade
}
