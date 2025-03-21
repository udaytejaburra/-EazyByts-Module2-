package com.app.stocktrading.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;  // e.g., AAPL, GOOGL, TSLA

    @Column(nullable = false)
    private String name;    // Full company name

    @Column(nullable = false)
    private Double price;   // Current stock price
}
