package com.crypto.trade.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum Side {
    BUY("BUY"),
    SELL("SELL");
    private final String value;

    Side(String value) {
        this.value = value;
    }

    public static Side fromString(String side) {
        return Arrays.stream(Side.values())
                .filter(s -> s.getValue().equalsIgnoreCase(side)).findFirst().orElse(null);
    }

    public static boolean in(String side) {
        return Objects.nonNull(fromString(side));
    }
}
