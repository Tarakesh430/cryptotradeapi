package com.crypto.trade.api.repository;

import com.crypto.trade.api.entity.CryptoExchange;
import com.crypto.trade.api.entity.CryptoOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoOrderRepository extends JpaRepository<CryptoOrder,String> {
    Optional<CryptoOrder> findByGlobalOrderUid(String globalOrderId);
}
