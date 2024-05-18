package com.crypto.trade.api.entity;

import com.crypto.trade.api.enums.OrderStatus;
import com.crypto.trade.api.enums.Side;
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
    private String symbol;
    private String price;
    private String averagePrice;
    private String originalQty;
    private String executedQty;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private Side side;
    private String orderSource;

    private Long createdTime;
    private Long updatedTime;

    @ManyToOne
    @JoinColumn(name ="cryptoExchangeId",nullable =false)
    private CryptoExchange cryptoExchange;

}
