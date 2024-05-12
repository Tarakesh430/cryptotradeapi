package com.crypto.trade.api.mapper;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {



    OrderResponse toOrderResponse(CryptoOrder coinSwitchOrderResponse);

    List<OrderResponse> toOrderResponse(List<CoinSwitchOrderResponse> coinSwitchOrderResponse);


}
