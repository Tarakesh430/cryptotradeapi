package com.crypto.trade.api.service;

import com.crypto.trade.api.handlers.GetActiveCoinsHandler;
import com.crypto.trade.api.response.CoinSwitchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlatformService {
    private final Logger logger = LoggerFactory.getLogger(PlatformService.class);
    private final GetActiveCoinsHandler getActiveCoinsHandler;
    public CoinSwitchResponse<Map<String, List<String>>> getAllActiveCoins(String exchange)
            throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
       return getActiveCoinsHandler.getActiveCoins(exchange);
    }
}
