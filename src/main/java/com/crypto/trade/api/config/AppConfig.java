package com.crypto.trade.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
    @Value("${coinswitch.trade.api.baseUrl}")
    private String coinswitchBaseUrl;

    @Bean
    public RestClient restClient() {
        System.out.println(coinswitchBaseUrl);
        return RestClient.create(coinswitchBaseUrl);
    }

    @Bean("objectMapper")
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
