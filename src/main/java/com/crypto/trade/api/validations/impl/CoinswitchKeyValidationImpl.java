package com.crypto.trade.api.validations.impl;

import com.crypto.trade.api.exception.KeyValidationException;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.crypto.trade.api.validations.KeyValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component("coinswitchKeyValidation")
@RequiredArgsConstructor
public class CoinswitchKeyValidationImpl implements KeyValidation {
    private final Logger logger = LoggerFactory.getLogger(CoinswitchKeyValidationImpl.class);
    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;

    @Value("${coinswitch.trade.api.key}")
    private String apiKey;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    private final String VALIDATE_KEY_PATH = "/validate/keys";
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    @Override
    public boolean validateKeys() throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException, KeyValidationException {
        String signature = coinSwitchSignatureGeneration.generateSignature(HttpMethod.GET.name(),
                getValidatePath(), new HashMap<>(), new HashMap<>());
        String response = restClient.get().uri(getValidatePath())
                .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
                .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
                .exchange((request, resp) -> {
                    if (resp.getStatusCode().equals(HttpStatus.ACCEPTED)) {
                        return SUCCESS;
                    }
                    logger.info("Request Failed With status code {} ", resp.getStatusCode());
                    logger.info("Response Message {}", resp.getStatusText());
                    return FAILURE;
                });
        switch (response) {
            case SUCCESS:
                logger.info("Key Validation Successfull ");
                break;
            case FAILURE:
                logger.info("Key Validation Failed ");
                throw new KeyValidationException("Key Validation Failed for the CoinSwitch");
            default:
                logger.info("Pls check the code for Implementation issues");
        }
        return false;
    }

    public String getValidatePath() {
        return baseUrl.concat(VALIDATE_KEY_PATH);
    }

}
