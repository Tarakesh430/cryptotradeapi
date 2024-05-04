package com.crypto.trade.api.health.impl;

import com.crypto.trade.api.health.HealthApi;
import com.crypto.trade.api.response.CoinSwitchResponse;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
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

@Component("coinSwitchHealthApi")
@RequiredArgsConstructor
public class CoinSwitchHealthApi implements HealthApi {
    private final Logger logger = LoggerFactory.getLogger(CoinSwitchHealthApi.class);
    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;

    @Value("${coinswitch.trade.api.key}")
    private String apiKey;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;
    @Override
    public void checkHealth() throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
//        String signature = coinSwitchSignatureGeneration.generateSignature(HttpMethod.GET.name(),
//                getHealthApiPath(), new HashMap<>(), new HashMap<>());
//        CoinSwitchResponse response = restClient.get().uri(getHealthApiPath())
//                .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
//                .header(CommonConstants.CS_AUTH_APIKEY, apiKey).retrieve().body(CoinSwitchResponse.class);
//        if(HttpStatus.OK.getReasonPhrase().equalsIgnoreCase(response.getMessage())){
//            logger.info("Coin Switch Health Good and Ready to use {}",response);
//            return;
//        }
//        logger.info("The response from CheckHealth for CoinSwitch is {}",response);
    }

    private String getHealthApiPath() {
        return baseUrl.concat("ping");
    }
}
