package com.crypto.trade.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeDetailsResponse {
    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("m")
    private boolean isBuyerMaker;

    @JsonProperty("p")
    private String price;

    @JsonProperty("q")
    private String quantity;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("t")
    private String tradeId;

    @JsonProperty("e")
    private String exchange;
}
