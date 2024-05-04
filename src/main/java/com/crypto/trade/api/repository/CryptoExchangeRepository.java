package com.crypto.trade.api.repository;

import com.crypto.trade.api.entity.CryptoExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoExchangeRepository extends JpaRepository<CryptoExchange,Long> {
    List<CryptoExchange> findAllByIsActive(Integer isActive);
}
