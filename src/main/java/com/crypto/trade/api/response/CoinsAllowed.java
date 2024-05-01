package com.crypto.trade.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CoinsAllowed implements Serializable {
    private String walletType;
    private List<Coin> coins;
}
