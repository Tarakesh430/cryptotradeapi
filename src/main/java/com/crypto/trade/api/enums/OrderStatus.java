package com.crypto.trade.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum OrderStatus {
    OPEN("OPEN"),
    PARTIALLY_EXECUTED("PARTIALLY_EXECUTED"),
    CANCELLED("CANCELLED"),
    EXECUTED("EXECUTED"),
    EXPIRED("EXPIRED"),
    DISCARDED("DISCARDED"),
    CANCELLATION_RAISED("CANCELLATION_RAISED"),
    EXPIRATION_RAISED("EXPIRATION_RAISED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public OrderStatus fromString(String orderStatus) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.getValue().equalsIgnoreCase(orderStatus)).findFirst().orElse(null);
    }

    public boolean in(String orderStatus) {
        return Objects.nonNull(fromString(orderStatus));
    }
}
