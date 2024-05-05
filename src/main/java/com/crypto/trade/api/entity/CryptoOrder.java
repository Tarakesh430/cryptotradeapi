package com.crypto.trade.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@Table(name = "crypto_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoOrder implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uid", unique = true)
    private String globalOrderUid;
    private String orderId;
    private Long createdTime;
    private Long updatedTime;

    @ManyToOne
    @JoinColumn(name ="cryptoExchangeId",nullable =false)
    private CryptoExchange cryptoExchange;

}
