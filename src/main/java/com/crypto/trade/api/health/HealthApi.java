package com.crypto.trade.api.health;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface HealthApi {
    void checkHealth() throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException;
}
