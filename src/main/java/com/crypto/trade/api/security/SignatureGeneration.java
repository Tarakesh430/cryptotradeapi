package com.crypto.trade.api.security;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

public interface SignatureGeneration {
     String generateSignature(String secretKey,String method, String endPoint, Map<String,Object> payload, Map<String,String> params)
             throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException;
}
