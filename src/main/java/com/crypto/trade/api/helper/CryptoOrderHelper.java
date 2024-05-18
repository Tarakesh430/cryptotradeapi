package com.crypto.trade.api.helper;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.enums.OrderStatus;
import com.crypto.trade.api.enums.Side;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import org.springframework.stereotype.Component;

@Component
public class CryptoOrderHelper {
    public CryptoOrder createCryptoOrder(CoinSwitchOrderResponse coinSwitchOrderResponse) {
        CryptoOrder cryptoOrder = new CryptoOrder();
        cryptoOrder.setOrderId(coinSwitchOrderResponse.getOrderId());
        cryptoOrder.setSymbol(coinSwitchOrderResponse.getSymbol());
        cryptoOrder.setPrice(String.valueOf(coinSwitchOrderResponse.getPrice()));
        cryptoOrder.setAveragePrice(coinSwitchOrderResponse.getAveragePrice());
        cryptoOrder.setOriginalQty(String.valueOf(coinSwitchOrderResponse.getOrigQty()));
        cryptoOrder.setExecutedQty(String.valueOf(coinSwitchOrderResponse.getExecutedQty()));
        cryptoOrder.setStatus(OrderStatus.fromString(coinSwitchOrderResponse.getStatus()));
        cryptoOrder.setSide(Side.fromString(coinSwitchOrderResponse.getSide()));
        cryptoOrder.setOrderSource(coinSwitchOrderResponse.getOrderSource());
        cryptoOrder.setCreatedTime(coinSwitchOrderResponse.getCreatedTime());
        cryptoOrder.setUpdatedTime(coinSwitchOrderResponse.getUpdatedTime());
        return cryptoOrder;
    }


}
