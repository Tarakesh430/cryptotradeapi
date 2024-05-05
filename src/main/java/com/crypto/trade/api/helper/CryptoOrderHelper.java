package com.crypto.trade.api.helper;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import org.springframework.stereotype.Component;

@Component
public class CryptoOrderHelper {
    public CryptoOrder createCryptoOrder(CoinSwitchOrderResponse coinSwitchOrderResponse) {
        CryptoOrder cryptoOrder = new CryptoOrder();
        cryptoOrder.setOrderId(coinSwitchOrderResponse.getOrderId());
        cryptoOrder.setCreatedTime(coinSwitchOrderResponse.getCreatedTime());
        cryptoOrder.setUpdatedTime(coinSwitchOrderResponse.getUpdatedTime());
        return cryptoOrder;
    }
}
