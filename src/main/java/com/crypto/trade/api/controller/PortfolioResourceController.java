package com.crypto.trade.api.controller;

import com.crypto.trade.api.dto.CryptoExchangeDto;
import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.response.PortFolio;
import com.crypto.trade.api.service.CryptoExchangeService;
import com.crypto.trade.api.service.PortFolioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cryptotrade")
public class PortfolioResourceController {
    private final Logger logger = LoggerFactory.getLogger(CryptoResourceController.class);
    private final PortFolioService portFolioService;

    @GetMapping("/portfolio")
    public ApiResponse<List<PortFolio>> getPortFolioDetails(@RequestParam("exchange") String exchange, @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("GET :: ACTIVE CRYPTO EXCHANGES :: LIST");
            return ApiResponse.success("Successfully Retrieved Crypto Exchanges", portFolioService.getPortFolioDetails(exchange,httpHeaders));
        } catch (Exception ex) {
            logger.error("The exception for retrieving Crypto Exchanges ", ex);
            return ApiResponse.error("Error in retrieving Crypto Exchanges", ex.getMessage());
        }
    }
}
