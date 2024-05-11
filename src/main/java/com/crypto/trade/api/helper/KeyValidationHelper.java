package com.crypto.trade.api.helper;

import com.crypto.trade.api.request.KeyValidationRequest;
import com.crypto.trade.api.utils.constants.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class KeyValidationHelper {
    private final Logger logger = LoggerFactory.getLogger(KeyValidationHelper.class);
    public KeyValidationRequest createKeyValidationRequest(String exchange, HttpHeaders httpHeaders) throws Exception {
        logger.info("Create Key Validation Request for exchange {} ",exchange);
        KeyValidationRequest keyValidationRequest = new KeyValidationRequest();
        keyValidationRequest.setExchangeName(exchange);
        if (exchange.equals("coinswitchx")) {
            createKeyValidationForCoinswitchx(keyValidationRequest, httpHeaders);
        }else{
            throw new Exception("Illegel Exchange Passed in request");
        }
        return keyValidationRequest;
    }

    private KeyValidationRequest createKeyValidationForCoinswitchx(KeyValidationRequest keyValidationRequest, HttpHeaders httpHeaders) {
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);
        keyValidationRequest.setApiKey(apiKey);
        keyValidationRequest.setSecretKey(secretKey);
        return keyValidationRequest;
    }
}
