package com.crypto.trade.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyValidationRequest implements Serializable {
    @JsonProperty("exchange_name")
    private String exchangeName;

    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("api_key")
    private String apiKey;
}