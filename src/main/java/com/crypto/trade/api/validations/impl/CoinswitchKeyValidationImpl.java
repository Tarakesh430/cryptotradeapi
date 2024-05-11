package com.crypto.trade.api.validations.impl;

import com.crypto.trade.api.exception.KeyValidationException;
import com.crypto.trade.api.request.KeyValidationRequest;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.CoinSwitchCommonFunctions;
import com.crypto.trade.api.validations.KeyValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static com.crypto.trade.api.utils.constants.CoinswitchApiPaths.VALIDATE_KEY_PATH;

@Component("coinswitchx")
@RequiredArgsConstructor
public class CoinswitchKeyValidationImpl implements KeyValidation {
    private final Logger logger = LoggerFactory.getLogger(CoinswitchKeyValidationImpl.class);
    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    private final CoinSwitchCommonFunctions coinSwitchCommonFunctions;
    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";


    @Override
    public boolean validateKeys(KeyValidationRequest keyValidationRequest) throws UnsupportedEncodingException,
            URISyntaxException, JsonProcessingException, KeyValidationException {
        validateRequest(keyValidationRequest);
        String signature = coinSwitchSignatureGeneration.generateSignature(keyValidationRequest.getSecretKey(), HttpMethod.GET.name(),
                getValidatePath(), new HashMap<>(), new HashMap<>());
        String response = restClient.get().uri(baseUrl.concat(getValidatePath()))
                .headers(httpHeaders -> httpHeaders.putAll(coinSwitchCommonFunctions.getHeaders(signature,
                        keyValidationRequest.getApiKey())))
                .exchange((request, resp) -> {
                    if (resp.getStatusCode().equals(HttpStatus.OK)) {
                        return SUCCESS;
                    }
                    logger.info("Request Failed With status code {} ", resp.getStatusCode());
                    logger.info("Response Message {}", resp.getStatusText());
                    return FAILURE;
                });
        switch (response) {
            case SUCCESS:
                logger.info("Key Validation Successfully ");
                break;
            case FAILURE:
                logger.info("Key Validation Failed ");
                throw new KeyValidationException("Key Validation Failed for CoinSwitch Exchange");
            default:
                logger.info("Pls check the code for Implementation issues");
        }
        return true;
    }

    private void validateRequest(KeyValidationRequest keyValidationRequest) throws KeyValidationException {
        if (StringUtils.isBlank(keyValidationRequest.getSecretKey()) ||
                StringUtils.isBlank(keyValidationRequest.getApiKey())) {
            throw new KeyValidationException("Empty Values Passed in the Request for the exchange");
        }
    }

    public String getValidatePath() {
        return VALIDATE_KEY_PATH;
    }
}
