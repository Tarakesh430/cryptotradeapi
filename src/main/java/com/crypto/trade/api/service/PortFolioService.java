package com.crypto.trade.api.service;

import com.crypto.trade.api.entity.CryptoExchange;
import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.handlers.LoadHandlerHelper;
import com.crypto.trade.api.repository.CryptoExchangeRepository;
import com.crypto.trade.api.repository.CryptoOrderRepository;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.response.PortFolio;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortFolioService {

    private final Logger logger = LoggerFactory.getLogger(PortFolioService.class);
    private final LoadHandlerHelper loadHandlerHelper;
    private final CryptoExchangeRepository cryptoExchangeRepository;
    private final CryptoOrderRepository cryptoOrderRepository;


    public List<PortFolio> getPortFolioDetails(String exchange, HttpHeaders httpHeaders) throws Exception {
        //Check for the Exchange is available

        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(exchange, "getPortFolio");

        HandlerContext<String,String> handlerContext = HandlerContext.<String,String>builder().
                exchange(exchange).httpHeaders(httpHeaders).build();
        handler.process(handlerContext);
        return handlerContext.getPortFolio();
    }
}
