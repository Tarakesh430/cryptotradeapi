package com.crypto.trade.api.response;

import com.crypto.trade.api.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Order {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("price")
    private String price;
    @JsonProperty("average_price")
    private String averagePrice;
    @JsonProperty("org_qty")
    private String origQty;
    @JsonProperty("executed_qty")
    private String executedQty;
    @JsonProperty("status")
    private OrderStatus status;
    @JsonProperty("side")
    private String side;
    @JsonProperty("order_source")
    private String orderSource;
    @JsonProperty("created_time")
    private Long createdTime;
    @JsonProperty("updated_time")
    private Long updatedTime;
}
