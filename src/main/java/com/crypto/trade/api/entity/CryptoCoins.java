package com.crypto.trade.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "crypto_coins")
@Data
public class CryptoCoins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coin_name")
    private String coinName;

    @Column(name ="trade_symbol")
    private String tradeSymbol;

    @Column(name ="isActive")
    private String isActive;
}
