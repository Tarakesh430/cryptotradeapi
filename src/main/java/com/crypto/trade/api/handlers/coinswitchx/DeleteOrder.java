package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.mapper.OrderMapper;
import com.crypto.trade.api.repository.CryptoOrderRepository;
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

@Component("coinswitchx_deleteOrder")
@RequiredArgsConstructor
public class DeleteOrder {

    private final Logger logger = LoggerFactory.getLogger(DeleteOrder.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    private final OrderMapper orderMapper;
    private final CryptoOrderRepository cryptoOrderRepository;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    public <K,V> void process(HandlerContext<K,V> handlerContext) throws Exception {
        CryptoOrder cryptoOrder = handlerContext.getCryptoOrder();

        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        logger.info("Cancel Order for Order Id {}", cryptoOrder.getOrderId());
        String path = getPath();
        path = path.concat("?order_id=" + URLEncoder.encode(cryptoOrder.getOrderId(), StandardCharsets.UTF_8));
        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.DELETE.name(),
                path, new HashMap<>(), new HashMap<>());
        Response<CoinSwitchOrderResponse> response = null;
        try {
            response = restClient.delete().uri(baseUrl.concat(path))
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
        handlerContext.setOrderResponse(orderMapper.toOrderResponse(response.getData(), cryptoOrder));
    }

    private String getPath() {
        return "/trade/api/v2/order";
    }
}
