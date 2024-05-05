package com.crypto.trade.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest implements Serializable {
    @JsonProperty("side")
    private String side;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("type")
    private String type;

    @JsonProperty("price")
    private double price;

    @JsonProperty("quantity")
    private double quantity;

    @JsonProperty("exchange")
    private String exchange;

}
