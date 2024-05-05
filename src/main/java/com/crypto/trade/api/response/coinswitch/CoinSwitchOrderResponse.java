package com.crypto.trade.api.response.coinswitch;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CoinSwitchOrderResponse {
    @JsonProperty("order_id")
    private String orderId;

    private String symbol;
    private double price;

    @JsonProperty("average_price")
    private String averagePrice;

    @JsonProperty("orig_qty")
    private double origQty;

    @JsonProperty("executed_qty")
    private double executedQty;
    private String status;
    private String side;
    private String exchange;

    @JsonProperty("order_source")
    private String orderSource;

    @JsonProperty("created_time")
    private Long createdTime;

    @JsonProperty("updated_time")
    private Long updatedTime;
}
