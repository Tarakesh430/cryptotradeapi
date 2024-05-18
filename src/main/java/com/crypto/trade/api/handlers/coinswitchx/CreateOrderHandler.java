package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.entity.CryptoOrder;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.helper.CryptoOrderHelper;
import com.crypto.trade.api.mapper.OrderMapper;
import com.crypto.trade.api.repository.CryptoExchangeRepository;
import com.crypto.trade.api.repository.CryptoOrderRepository;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.request.OrderRequest;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.response.Response;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component("coinswitchx_createOrder")
@RequiredArgsConstructor
public class CreateOrderHandler implements BaseHandler {
    private final Logger logger = LoggerFactory.getLogger(CreateOrderHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    private final ObjectMapper objectMapper;
    private final CryptoOrderHelper cryptoOrderHelper;
    private final CryptoOrderRepository cryptoOrderRepository;
    private final CryptoExchangeRepository cryptoExchangeRepository;
    private final OrderMapper orderMapper;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    @Override
    public <V> void process(HandlerContext<V> handlerContext) throws Exception {
        OrderRequest orderRequest = handlerContext.getOrderRequest();

        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        logger.info("Order Request Received {}", orderRequest);
        String path = getPath();
        HashMap<String, Object> payload = new HashMap<>();

        payload.put("side", orderRequest.getSide());
        payload.put("symbol", orderRequest.getSymbol());
        payload.put("type", orderRequest.getType());
        payload.put("quantity", orderRequest.getQuantity());
        payload.put("price", orderRequest.getPrice());
        payload.put("exchange", orderRequest.getExchange());

        DecimalFormat decimalFormat = new DecimalFormat("#.################");
        String formattedQuantity = decimalFormat.format(payload.get("quantity"));
        String formattedPrice = decimalFormat.format(payload.get("price"));
        BigDecimal price = new BigDecimal(formattedPrice);
        BigDecimal quantity = new BigDecimal(formattedQuantity);
        payload.put("quantity", quantity);
        payload.put("price", price);
        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.POST.name(),
                path, payload, new HashMap<>());
        Response<CoinSwitchOrderResponse> response = null;
        try {
            response = restClient.post().uri(baseUrl.concat(path))
                    .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
                    .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
                    .contentType(APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(payload))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
            });
        } catch (Exception ex) {
            logger.info("Exception ex {}", ex.getMessage());
        }
        if (Objects.isNull(response) || Objects.isNull(response.getData())) {
            logger.error("Exception in getting Order Details for Exchange {}   path {}",
                    handlerContext.getExchange(),path);
            throw new Exception("Exception in placing Order ");
        }
        CryptoOrder cryptoOrder = cryptoOrderHelper.createCryptoOrder(response.getData());
        cryptoOrder.setCryptoExchange(cryptoExchangeRepository.findByExchangeName(orderRequest.getExchange())
                .orElse(null));
        cryptoOrderRepository.save(cryptoOrder);
        handlerContext.setOrderResponse(orderMapper.toOrderResponse(cryptoOrder));
    }
    @Override
    public String getPath() {
        return "/trade/api/v2/order";
    }

}
