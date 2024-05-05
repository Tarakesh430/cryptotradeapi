package com.crypto.trade.api.service;

import com.crypto.trade.api.entity.CryptoExchange;
import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.handlers.LoadHandlerHelper;
import com.crypto.trade.api.repository.CryptoExchangeRepository;
import com.crypto.trade.api.repository.CryptoOrderRepository;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoOrderService {
    private final Logger logger = LoggerFactory.getLogger(CryptoOrderService.class);
    private final LoadHandlerHelper loadHandlerHelper;
    private final CryptoExchangeRepository cryptoExchangeRepository;
    private final CryptoOrderRepository cryptoOrderRepository;

    public OrderResponse getOrderDetails(String exchange, String globalOrderId, HttpHeaders httpHeaders) throws Exception {
        //Check for the Exchange is available
        logger.info("Retrieve Crypto Order Details for Exchange {} Global Order Id {}",exchange,globalOrderId);
        CryptoExchange cryptoExchange = cryptoExchangeRepository.findByExchangeName(exchange)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Exchange Passed"));
        CryptoOrder cryptoOrder = cryptoOrderRepository.findByGlobalOrderUid(globalOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Global Order Id Passed"));
        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(cryptoExchange.getExchangeName(), "getOrderDetails");

        HandlerContext handlerContext = HandlerContext.builder().cryptoOrder(cryptoOrder).
                exchange(exchange).httpHeaders(httpHeaders).build();
        handler.process(handlerContext);
        return handlerContext.getOrderResponse();
    }
}
