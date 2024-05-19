package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.enums.OrderStatus;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.mapper.OrderMapper;
import com.crypto.trade.api.repository.CryptoOrderRepository;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Component("coinswitchx_deleteOrder")
@RequiredArgsConstructor
public class DeleteOrderHandler implements BaseHandler {

    private final Logger logger = LoggerFactory.getLogger(DeleteOrderHandler.class);
    private final RestTemplate restTemplate;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;
    private final CryptoOrderRepository  cryptoOrderRepository;


    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    @Override
    public <V> void process(HandlerContext<V> handlerContext) throws Exception {
        CryptoOrder cryptoOrder = handlerContext.getCryptoOrder();
       if(Objects.isNull(cryptoOrder)){
           throw new Exception("Crypto Order not populated");
       }
        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        logger.info("Cancel Order for Order Id {}", cryptoOrder.getOrderId());
        String path = getPath();

        Map<String, Object> payload = new HashMap<>();
        payload.put("order_id", cryptoOrder.getOrderId());
        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.DELETE.name(),
                path, payload, new HashMap<>());
        ResponseEntity<CoinSwitchOrderResponse> response = null;
        try {

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set(CommonConstants.CS_AUTH_SIGNATURE, signature);
            requestHeaders.set(CommonConstants.CS_AUTH_APIKEY, apiKey);
            requestHeaders.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(payload), requestHeaders);
            response = restTemplate.exchange(baseUrl.concat(path), HttpMethod.DELETE, entity, new ParameterizedTypeReference<>() {
            });
        } catch (Exception ex) {
            logger.info("Exception ex {}", ex.getMessage());
        }
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            logger.error("Exception in deleting Order in exchange {} order Id {}, Path {}",
                    handlerContext.getExchange(), cryptoOrder.getOrderId(), path);
            throw new Exception("Exception in deleting Order Id " + cryptoOrder.getOrderId());
        }
        updateCryptoOrder(cryptoOrder,response.getBody());
        cryptoOrderRepository.save(cryptoOrder);
        handlerContext.setOrderResponse(orderMapper.toOrderResponse(cryptoOrder));
    }

    private void updateCryptoOrder(CryptoOrder cryptoOrder, CoinSwitchOrderResponse body) {
        cryptoOrder.setStatus(OrderStatus.fromString(body.getStatus()));
        cryptoOrder.setExecutedQty(String.valueOf(body.getExecutedQty()));
        cryptoOrder.setUpdatedTime(body.getUpdatedTime());
        cryptoOrder.setOrderSource(body.getOrderSource());
    }

    @Override
    public String getPath() {
        return "/trade/api/v2/order";
    }
}
