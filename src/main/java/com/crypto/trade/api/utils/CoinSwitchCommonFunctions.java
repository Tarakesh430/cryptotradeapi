package com.crypto.trade.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        logger.info("Signature Message for Request {}",signatureMessage);
        return signatureMessage;
    }
}
