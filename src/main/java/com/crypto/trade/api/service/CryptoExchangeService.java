package com.crypto.trade.api.service;


import com.crypto.trade.api.dto.CoinDto;
import com.crypto.trade.api.dto.CryptoExchangeDto;
import com.crypto.trade.api.entity.CryptoExchange;
import com.crypto.trade.api.exception.KeyValidationException;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.handlers.LoadHandlerHelper;
import com.crypto.trade.api.mapper.CryptoExchangeMapper;
import com.crypto.trade.api.repository.CryptoExchangeRepository;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.request.KeyValidationRequest;
import com.crypto.trade.api.validations.KeyValidationFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoExchangeService {
    private final Logger logger = LoggerFactory.getLogger(CryptoExchangeService.class);
    private final CryptoExchangeRepository cryptoExchangeRepository;
    private final CryptoExchangeMapper mapper;
    private final KeyValidationFactory keyValidationFactory;
    private final LoadHandlerHelper loadHandlerHelper;

    public List<CryptoExchangeDto> getAllCryptoExchanges() {
        //Get all active crypto exchanges provided by the service
        List<CryptoExchange> cryptoExchanges = cryptoExchangeRepository.findAllByIsActive(1);
        logger.info("The active crypto exchanges retrieved are {}", cryptoExchanges);
        return Optional.ofNullable(cryptoExchanges).orElse(Collections.emptyList()).stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    public void validateUserKeys(KeyValidationRequest keyValidationRequest)
            throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException, KeyValidationException {
        logger.info("Validate User Keys for exchange {}", keyValidationRequest.getExchangeName());
        //No validation Required Exchnage related Validation on the Request
        // will be carried out in the Concrete Impl of KayValidation
        keyValidationFactory.keyValidationFactory(keyValidationRequest.getExchangeName())
                .validateKeys(keyValidationRequest);
        logger.info("Key Validation Success full");
    }

    public List<String> getAllActiveCoins(String exchange, HttpHeaders httpHeaders)
            throws Exception {
        logger.info("Get Active Trade Coins for EXchange {} ", exchange);
        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(exchange, "getActiveCoins");
        HandlerContext handlerContext = HandlerContext.builder().exchange(exchange).httpHeaders(httpHeaders).build();
        handler.process(handlerContext);
        return handlerContext.getCoins();
    }
}
