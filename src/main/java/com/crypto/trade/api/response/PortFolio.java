package com.crypto.trade.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PortFolio implements Serializable {
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("blocked_balance_order")
    private String blockedBalanceOrder;

    @JsonProperty("main_balance")
    private String mainBalance;

    @JsonProperty("buy_average_price")
    private double buyAveragePrice;

    @JsonProperty("invested_value")
    private double investedValue;

    @JsonProperty("invested_value_excluding_fee")
    private double investedValueExcludingFee;

    @JsonProperty("current_value")
    private double currentValue;

    @JsonProperty("sell_rate")
    private double sellRate;

    @JsonProperty("buy_rate")
    private double buyRate;

    @JsonProperty("is_average_price_available")
    private boolean isAveragePriceAvailable;

    @JsonProperty("name")
    private String name;

    @JsonProperty("is_delisted_coin")
    private boolean isDelistedCoin;
}
