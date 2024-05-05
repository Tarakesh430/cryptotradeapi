package com.crypto.trade.api.mapper;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(target ="orderId" , source = "coinSwitchOrderResponse.orderId")
    @Mapping(target ="createdTime",source ="coinSwitchOrderResponse.createdTime")
    @Mapping(target ="updatedTime" ,source ="coinSwitchOrderResponse.updatedTime")
    OrderResponse toOrderResponse(CoinSwitchOrderResponse coinSwitchOrderResponse, CryptoOrder cryptoOrder);


}
