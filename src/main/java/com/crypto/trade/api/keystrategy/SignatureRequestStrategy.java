package com.crypto.trade.api.keystrategy;

public interface SignatureRequestStrategy {
    String generate(String request,String secretKey);
}
