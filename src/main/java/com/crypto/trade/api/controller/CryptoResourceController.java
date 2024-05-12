package com.crypto.trade.api.controller;

import com.crypto.trade.api.dto.CryptoExchangeDto;
import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.response.DepthDetailsResponse;
import com.crypto.trade.api.service.CryptoExchangeService;
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
public class CryptoResourceController {
    private final Logger logger = LoggerFactory.getLogger(CryptoResourceController.class);
    private final CryptoExchangeService cryptoExchangeService;

    @GetMapping("/")
    public String getHelloWorld() {
        return "Hello- World";
    }

    @GetMapping("/crypto-exchanges")
    public ResponseEntity<ApiResponse<List<CryptoExchangeDto>>> getCryptoExchanges() {
        try {
            logger.info("GET :: ACTIVE CRYPTO EXCHANGES :: LIST");
            return ResponseEntity.ok(ApiResponse.success("Successfully Retrieved Crypto Exchanges",
                    cryptoExchangeService.getAllCryptoExchanges()));
        } catch (Exception ex) {
            logger.error("The exception for retrieving Crypto Exchanges ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error in retrieving Crypto Exchanges", ex.getMessage()));
        }
    }

    /**
     * Validate User Keys against an exchange
     *
     * @param exchange    Request Param Exchange
     * @param httpHeaders Http Headers
     * @return Response  Entity
     */
    @PostMapping("/validate-keys")
    public ResponseEntity<ApiResponse<Void>> validateUserKeys(@RequestParam("exchange") String exchange, @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("POST::KEYS FOR EXCHANGE:: VALIDATION");
            logger.info("Key Validation Request for exchange {}", exchange);
            cryptoExchangeService.validateUserKeys(exchange, httpHeaders);
            logger.info("Validation Success full");
            return ResponseEntity.ok(ApiResponse.success("Validation Successfull", null));
        } catch (Exception ex) {
            logger.info("Validation Failed with Exception", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Validation Failed", ex.getMessage()));
        }
    }

    @GetMapping("/active-coins")
    public ApiResponse<List<String>> getActiveCoins(@RequestParam("exchange") String exchange, @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("GET :: ACTIVE CRYPTO EXCHANGES :: LIST");
            return ApiResponse.success("Successfully Retrieved Crypto Exchanges",
                    cryptoExchangeService.getAllActiveCoins(exchange, httpHeaders));
        } catch (Exception ex) {
            logger.error("The exception for retrieving Crypto Exchanges ", ex);
            return ApiResponse.error("Error in retrieving Crypto Exchanges", ex.getMessage());
        }
    }

    @GetMapping("/depth")
    public ResponseEntity<ApiResponse<DepthDetailsResponse>> getDepthDetails(@RequestParam("exchange") String exchange, @RequestParam("symbol") String symbol,
                                                                             @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("GET::DEPTH DETAILS FOR EXCHANGE:: COIN");
            logger.info("Get Depth Details for Exchange {} Symbol {}", exchange, symbol);
            DepthDetailsResponse depthDetails = cryptoExchangeService.getDepthDetails(exchange, symbol, httpHeaders);
            logger.info("Depth Details retrieved Successfully {}", depthDetails);
            return ResponseEntity.ok(ApiResponse.success("Depth Details Retrieved Successfully", depthDetails));
        } catch (Exception ex) {
            logger.info("Exception in getting Depth Details", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Exception in Getting Depth Details", ex.getMessage()));
        }
    }

    @GetMapping("/trade")
    public ResponseEntity<ApiResponse<DepthDetailsResponse>> getTradeDetails(@RequestParam("exchange") String exchange, @RequestParam("symbol") String symbol,
                                                                             @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("GET::DEPTH DETAILS FOR EXCHANGE:: COIN");
            logger.info("Get Depth Details for Exchange {} Symbol {}", exchange, symbol);
            DepthDetailsResponse depthDetails = cryptoExchangeService.getDepthDetails(exchange, symbol, httpHeaders);
            logger.info("Depth Details retrieved Successfully {}", "df");
            return ResponseEntity.ok(ApiResponse.success("Depth Details Retrieved Successfully", depthDetails));
        } catch (Exception ex) {
            logger.info("Exception in getting Depth Details", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Exception in Getting Depth Details", ex.getMessage()));
        }
    }

}