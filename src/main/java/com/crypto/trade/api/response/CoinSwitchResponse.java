package com.crypto.trade.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CoinSwitchResponse<K> implements Serializable {
    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private K data;
}
