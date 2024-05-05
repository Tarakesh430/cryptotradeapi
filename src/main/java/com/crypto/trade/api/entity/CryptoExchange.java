package com.crypto.trade.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "crypto_exchange")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "exchange_name")
    private String exchangeName;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_time")
    private Long createdTime;

    @Column(name = "updated_time")
    private Long updatedTime;

    @OneToMany(mappedBy = "cryptoExchange")
    private Set<CryptoOrder> cryptoOrders;
}