package com.crypto.trade.api.controller;
import com.crypto.trade.api.dto.CoinDto;
import com.crypto.trade.api.dto.CryptoExchangeDto;
import com.crypto.trade.api.request.KeyValidationRequest;
import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.service.CryptoExchangeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cryptotrade")
public class CryptoResourceController {
    private final Logger logger = LoggerFactory.getLogger(CryptoResourceController.class);
    private final CryptoExchangeService cryptoExchangeService;

    @GetMapping("/")
    public String getHelloWorld(){
        return "Hello- World";
    }
    @GetMapping("/crypto-exchanges")
    public ApiResponse<List<CryptoExchangeDto>> getCryptoExchanges() {
        try {
            logger.info("GET :: ACTIVE CRYPTO EXCHANGES :: LIST");
            return ApiResponse.success("Successfully Retrieved Crypto Exchanges", cryptoExchangeService.getAllCryptoExchanges());
        } catch (Exception ex) {
            logger.error("The exception for retrieving Crypto Exchanges ", ex);
            return ApiResponse.error("Error in retrieving Crypto Exchanges", ex.getMessage());
        }
    }

    @PostMapping("/validate-keys")
    public ApiResponse<Void> validateUserKeys(@RequestBody KeyValidationRequest keyValidationRequest) {
        try {
            logger.info("POST::KEYS FOR EXCHANGE:: VALIDATION");
            logger.info("Key Validation Request for exchnage {}", keyValidationRequest.getExchangeName());
            cryptoExchangeService.validateUserKeys(keyValidationRequest);
            logger.info("Validation Success full");
            return ApiResponse.success("Validation Successfull", null);
        } catch (Exception ex) {
            logger.info("Validation Failed with Exception", ex);
            return ApiResponse.error("Validation Failed", ex.getMessage());
        }
    }
    @GetMapping("/active-coins")
    public ApiResponse<List<CoinDto>> getActiveCoins(@RequestParam("exchange") String exchange, @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("GET :: ACTIVE CRYPTO EXCHANGES :: LIST");

            return ApiResponse.success("Successfully Retrieved Crypto Exchanges",
                    cryptoExchangeService.getAllActiveCoins(exchange,httpHeaders));
        } catch (Exception ex) {
            logger.error("The exception for retrieving Crypto Exchanges ", ex);
            return ApiResponse.error("Error in retrieving Crypto Exchanges", ex.getMessage());
        }
    }


}