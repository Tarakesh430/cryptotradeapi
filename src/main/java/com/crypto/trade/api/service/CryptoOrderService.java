package com.crypto.trade.api.service;

import com.crypto.trade.api.entity.CryptoExchange;
import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.handlers.LoadHandlerHelper;
import com.crypto.trade.api.repository.CryptoExchangeRepository;
import com.crypto.trade.api.repository.CryptoOrderRepository;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.request.OrderRequest;
import com.crypto.trade.api.response.OrderResponse;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoOrderService {
    private final Logger logger = LoggerFactory.getLogger(CryptoOrderService.class);
    private final LoadHandlerHelper loadHandlerHelper;
    private final CryptoExchangeRepository cryptoExchangeRepository;
    private final CryptoOrderRepository cryptoOrderRepository;

    public OrderResponse getOrderDetails(String exchange, String globalOrderId, HttpHeaders httpHeaders) throws Exception {
        //Check for the Exchange is available
        logger.info("Retrieve Crypto Order Details for Exchange {} Global Order Id {}", exchange, globalOrderId);
        CryptoExchange cryptoExchange = cryptoExchangeRepository.findByExchangeName(exchange)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Exchange Passed"));
        CryptoOrder cryptoOrder = cryptoOrderRepository.findByGlobalOrderUid(globalOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Global Order Id Passed"));
        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(cryptoExchange.getExchangeName(), "getOrderDetails");

        HandlerContext<String,String> handlerContext = HandlerContext.<String,String>builder().cryptoOrder(cryptoOrder).
                exchange(exchange).httpHeaders(httpHeaders).build();
        handler.process(handlerContext);
        return handlerContext.getOrderResponse();
    }

    public OrderResponse placeOrder(OrderRequest orderRequest, HttpHeaders httpHeaders) throws Exception {
        //Check for the Exchange is available
        logger.info("Retrieve Crypto Order Details for Exchange {} ", orderRequest.getExchange());
        CryptoExchange cryptoExchange = cryptoExchangeRepository.findByExchangeName(orderRequest.getExchange())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Exchange Passed"));
        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(cryptoExchange.getExchangeName(), "createOrder");
        HandlerContext<String,String> handlerContext = HandlerContext.<String,String>builder().orderRequest(orderRequest).
                exchange(orderRequest.getExchange()).httpHeaders(httpHeaders).build();
        handler.process(handlerContext);
        return handlerContext.getOrderResponse();
    }


    public List<OrderResponse> getOrders(String count, String fromTime, String toTime, String side, String symbols,
                                         String exchange, String type, String status) throws Exception {
        logger.info("Get Orders Request Received");
        CryptoExchange cryptoExchange = cryptoExchangeRepository.findByExchangeName(exchange)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Exchange Passed"));
        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(cryptoExchange.getExchangeName(), "getOrders");
        // Populate the dataMap
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("count",count);
        dataMap.put("fromTime",fromTime);
        dataMap.put("toTime",toTime);
        dataMap.put("side",side);
        dataMap.put("symbols",symbols);
        dataMap.put("exchange",exchange);
        dataMap.put("type",type);
        dataMap.put("status",status);
        HandlerContext<String, String> handlerContext = HandlerContext.<String, String>builder().data(dataMap).build();
        handler.process(handlerContext);
        return handlerContext.getOrderResponseList();
    }

    public OrderResponse deleteOrder(String exchange, String globalOrderId, HttpHeaders httpHeaders) throws Exception {
        logger.info("Retrieve Crypto Order Details for Exchange {} Global Order Id {}", exchange, globalOrderId);
        CryptoExchange cryptoExchange = cryptoExchangeRepository.findByExchangeName(exchange)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Exchange Passed"));
        CryptoOrder cryptoOrder = cryptoOrderRepository.findByGlobalOrderUid(globalOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Global Order Id Passed"));
        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(cryptoExchange.getExchangeName(), "deleteOrder");

        HandlerContext<String,String> handlerContext = HandlerContext.<String,String>builder().cryptoOrder(cryptoOrder).
                exchange(exchange).httpHeaders(httpHeaders).build();
        handler.process(handlerContext);
        return handlerContext.getOrderResponse();
    }
}
