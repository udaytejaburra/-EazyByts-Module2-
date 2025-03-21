package com.app.stocktrading.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the user who owns this stock
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Stock owned
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    // Quantity of the stock the user owns
    @Column(nullable = false)
    private int quantity;
}
