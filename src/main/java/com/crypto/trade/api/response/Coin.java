package com.crypto.trade.api.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class Coin implements Serializable {
    private String coinName;
}
