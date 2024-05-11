package com.crypto.trade.api.request;


import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.response.PortFolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class HandlerContext<K,V> implements Serializable {
    private HttpHeaders httpHeaders;
    private String exchange;
    private List<String> coins;
    private CryptoOrder cryptoOrder;
    private OrderResponse orderResponse;
    private OrderRequest orderRequest;
    private Map<K,V> data;
    private List<OrderResponse> orderResponseList;
    private List<PortFolio> portFolio;
}
