package com.crypto.trade.api.request;


import com.crypto.trade.api.dto.CoinDto;
import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.response.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class HandlerContext implements Serializable {
    private HttpHeaders httpHeaders;
    private String exchange;
    private List<String> coins;
    private CryptoOrder cryptoOrder;
    private OrderResponse orderResponse;
    private OrderRequest orderRequest;
}
