package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.enums.OrderStatus;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.mapper.OrderMapper;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.Response;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

@Component("coinswitchx_getOrderDetails")
@RequiredArgsConstructor
public class GetOrderDetailsHandler implements BaseHandler {
    Logger logger = LoggerFactory.getLogger(GetOrderDetailsHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    private final OrderMapper orderMapper;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    public <V> void process(HandlerContext< V> handlerContext) throws Exception {
        CryptoOrder cryptoOrder = handlerContext.getCryptoOrder();

        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        logger.info("Get Order Details for Order Id {}", cryptoOrder.getOrderId());
        String path = getPath();
        path = path.concat("?order_id=" + URLEncoder.encode(cryptoOrder.getOrderId(), StandardCharsets.UTF_8));
        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.GET.name(),
                path, new HashMap<>(), new HashMap<>());
        Response<CoinSwitchOrderResponse> response = null;
        try {
            response = restClient.get().uri(baseUrl.concat(path))
                    .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
                    .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
                    .retrieve().body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception ex) {
            logger.info("Exception ex {}", ex.getMessage());
        }
        if (Objects.isNull(response) || Objects.isNull(response.getData())) {
            logger.error("Exception in getting Order Details for Exchange {}  Order Id {} path {}",
                    handlerContext.getExchange(), cryptoOrder.getOrderId(), path);
            throw new Exception("Exception in getting Order Details for Order Id " + cryptoOrder.getOrderId());
        }
        updateCryptoOrder(cryptoOrder,response.getData());
        handlerContext.setOrderResponse(orderMapper.toOrderResponse(cryptoOrder));
    }

    private void updateCryptoOrder(CryptoOrder cryptoOrder, CoinSwitchOrderResponse body) {
        cryptoOrder.setStatus(OrderStatus.fromString(body.getStatus()));
        cryptoOrder.setExecutedQty(String.valueOf(body.getExecutedQty()));
        cryptoOrder.setUpdatedTime(body.getUpdatedTime());
        cryptoOrder.setOrderSource(body.getOrderSource());
    }
    public String getPath() {
        return "/trade/api/v2/order";
    }
}
