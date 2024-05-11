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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cryptotrade")
public class PortfolioResourceController {
    private final Logger logger = LoggerFactory.getLogger(PortfolioResourceController.class);
    private final PortFolioService portFolioService;

    @GetMapping("/portfolio")
    public ResponseEntity<ApiResponse<List<PortFolio>>> getPortFolioDetails(@RequestParam("exchange") String exchange, @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("GET :: PORTFOLIO DETAILS :: OBJECT");
            return ResponseEntity.ok(ApiResponse.success("Successfully Retrieved PortFolio Details",
                    portFolioService.getPortFolioDetails(exchange,httpHeaders)));
        } catch (Exception ex) {
            logger.error("The exception for retrieving PortFolio Details ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error in retrieving Port Folio Details", ex.getMessage()));
        }
    }
}
