package com.crypto.trade.api.service;

import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.handlers.LoadHandlerHelper;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.PortFolioResponse;
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

    public List<PortFolioResponse> getPortFolioDetails(String exchange, HttpHeaders httpHeaders) throws Exception {
        //Check for the Exchange is available
        logger.info("Get PortFolio Details for Exchange {}",exchange);
        BaseHandler handler = (BaseHandler) loadHandlerHelper.loadHandlerBean(exchange, "getPortFolio");
        HandlerContext<String> handlerContext = HandlerContext.<String>builder().
                exchange(exchange).httpHeaders(httpHeaders).build();
        handler.process(handlerContext);
        return handlerContext.getPortFolio();
    }
}
