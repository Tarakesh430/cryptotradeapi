package com.crypto.trade.api.service;

import com.crypto.trade.api.controller.WalletResource;
import com.crypto.trade.api.handlers.PortFolioHandler;
import com.crypto.trade.api.response.CoinSwitchResponse;
import com.crypto.trade.api.response.PortFolio;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final Logger logger = LoggerFactory.getLogger(WalletResource.class);
    private final PortFolioHandler portFolioHandler;

    public CoinSwitchResponse<List<PortFolio>> getPortFolioDetails()
            throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
        return portFolioHandler.getPortFolioDetails();
    }
}
