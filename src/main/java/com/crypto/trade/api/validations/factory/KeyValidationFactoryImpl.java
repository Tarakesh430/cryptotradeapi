package com.crypto.trade.api.validations.factory;

import com.crypto.trade.api.validations.KeyValidation;
import com.crypto.trade.api.validations.KeyValidationFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KeyValidationFactoryImpl implements KeyValidationFactory {

    private final Logger logger = LoggerFactory.getLogger(KeyValidationFactoryImpl.class);
    private final Map<String, KeyValidation> factoryMap;

    @Override
    public KeyValidation keyValidationFactory(String exchange) {
        logger.info("Key Validation Object Requested for exchange {}", exchange);
        KeyValidation keyValidation = factoryMap.get(exchange);
        if (Objects.isNull(keyValidation)) {
            throw new IllegalArgumentException("Invalid Exchange Type provided " + exchange);
        }
        return keyValidation;
    }
}
