package com.crypto.trade.api.mapper;

import com.crypto.trade.api.dto.CryptoExchangeDto;
import com.crypto.trade.api.entity.CryptoExchange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CryptoExchangeMapper {
    @Mapping(target = "exchangeId", source = "cryptoExchange.id")
    CryptoExchangeDto toDto(CryptoExchange cryptoExchange);

    CryptoExchange toEntity(CryptoExchangeDto cryptoExchangeDto);
}
