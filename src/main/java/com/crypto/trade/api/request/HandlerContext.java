package com.crypto.trade.api.request;


import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.response.DepthDetailsResponse;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.response.PortFolioResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class HandlerContext<V> implements Serializable {
    private HttpHeaders httpHeaders;
    private String exchange;
    private List<String> coins;
    private CryptoOrder cryptoOrder;
    private OrderResponse orderResponse;
    private OrderRequest orderRequest;
    private Map<String, V> data;
    private List<OrderResponse> orderResponseList;
    private List<PortFolioResponse> portFolio;
    private DepthDetailsResponse depthDetailsResponse;
}
