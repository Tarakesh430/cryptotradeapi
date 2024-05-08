package com.crypto.trade.api.mapper;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(target ="orderId" , source = "coinSwitchOrderResponse.orderId")
    @Mapping(target ="createdTime",source ="coinSwitchOrderResponse.createdTime")
    @Mapping(target ="updatedTime" ,source ="coinSwitchOrderResponse.updatedTime")
    OrderResponse toOrderResponse(CoinSwitchOrderResponse coinSwitchOrderResponse, CryptoOrder cryptoOrder);

    List<OrderResponse> toOrderResponse(List<CoinSwitchOrderResponse> coinSwitchOrderResponse);


}
