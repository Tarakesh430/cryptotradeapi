package com.crypto.trade.api.utils;

import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
@AllArgsConstructor
public class CoinSwitchCommonFunctions {
    private final Logger logger = LoggerFactory.getLogger(CoinSwitchCommonFunctions.class);
    private final ObjectMapper objectMapper;

    public String signatureMessage(String method, String endPoint, Map<String, Object> payload) throws JsonProcessingException {
        // Sort payload and convert to JSON string
        TreeMap<String, Object> treeMap = new TreeMap<>(payload);
        String sortedPayloadJson = objectMapper.writeValueAsString(treeMap);

        // Combine method, endpoint, and sorted payload JSON
        String signatureMessage = method + endPoint + sortedPayloadJson;
        logger.info("Signature Message for Request {}", signatureMessage);
        return signatureMessage;
    }

    public MultiValueMap<String,String> getHeaders(String signature, String apiKey) {
        MultiValueMap<String, String> httpHeaders = new HttpHeaders();
        httpHeaders.add(CommonConstants.CS_AUTH_SIGNATURE, signature);
        httpHeaders.add(CommonConstants.CS_AUTH_APIKEY, apiKey);
        return httpHeaders;
    }

}
