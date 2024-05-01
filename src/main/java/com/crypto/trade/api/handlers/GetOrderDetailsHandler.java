package com.crypto.trade.api.handlers;

import com.crypto.trade.api.response.CoinSwitchResponse;
import com.crypto.trade.api.response.CoinsAllowed;
import com.crypto.trade.api.response.Order;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class GetOrderDetailsHandler {
    Logger logger = LoggerFactory.getLogger(GetOrderDetailsHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;

    @Value("${coinswitch.trade.api.key}")
    private String apiKey;

    public CoinSwitchResponse<Order> getOrderDetails(String orderId) throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
        logger.info("Get Order Details for Order Id {}", orderId);
        String path = getPath();
        path = path.concat("?&order_id=" + URLEncoder.encode(orderId, StandardCharsets.UTF_8));
        String signature = coinSwitchSignatureGeneration.generateSignature(HttpMethod.GET.name(),
                getPath(), new HashMap<>(), new HashMap<>());
        CoinSwitchResponse<Order> coinSwitchResponse = restClient.get().uri(path)
                .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
                .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
                .retrieve().body(new ParameterizedTypeReference<CoinSwitchResponse<Order>>() {
                });
        if (coinSwitchResponse == null) {
            logger.error("Exception in Getting Order Details for {} with path {}", orderId, path);
            throw new RuntimeException("Exception in getting Order Details " + orderId);
        }
        logger.info("Successfully retrieved Order Details for exchange {} path {}", orderId, path);
        logger.info("Order Details Fetched {}", coinSwitchResponse);
        return coinSwitchResponse;
    }

    private String getPath() {
        return "/order";
    }
}
