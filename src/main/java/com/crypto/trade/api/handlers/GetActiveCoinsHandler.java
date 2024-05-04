package com.crypto.trade.api.handlers;

import com.crypto.trade.api.response.CoinSwitchResponse;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class GetActiveCoinsHandler {
    private final Logger logger = LoggerFactory.getLogger(GetActiveCoinsHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;

    @Value("${coinswitch.trade.api.key}")
    private String apiKey;

    public CoinSwitchResponse<Map<String, List<String>>> getActiveCoins(String exchange) throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
//        logger.info("Get Active Coins for Exchange {}", exchange);
//        String path = getPath();
//        path = path.concat("?exchange=" + URLEncoder.encode(exchange, StandardCharsets.UTF_8));
//        String signature = coinSwitchSignatureGeneration.generateSignature(HttpMethod.GET.name(),
//                path, new HashMap<>(), new HashMap<>());
//        CoinSwitchResponse<Map<String, List<String>>> coinSwitchResponse = null;
//        try{
//            coinSwitchResponse = restClient.get().uri(path)
//                    .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
//                    .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
//                    .header("Accept","*/*")
//                    .retrieve().body(new ParameterizedTypeReference<>() {
//                    });
//        }catch(Exception ex){
//            logger.info("Exception ex {}",ex.getMessage());
//        }
//        if (coinSwitchResponse == null) {
//            logger.error("Exception in Getting Active Coins for exchange {} with path {}", exchange, path);
//            throw new RuntimeException("Exception in Getting Active Coins for exchange " + exchange);
//        }
//        logger.info("Successfully retrieved Actibve Coinst for exchange {} path{}", exchange, path);
//        logger.info("Active Coins Fetched {}", coinSwitchResponse);
//        return coinSwitchResponse;
        return null;
    }

    private String getPath() {
        return "/trade/api/v2/coins";
    }


}
