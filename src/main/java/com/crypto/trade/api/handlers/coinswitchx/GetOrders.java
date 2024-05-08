package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.enums.OrderStatus;
import com.crypto.trade.api.enums.Side;
import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.mapper.OrderMapper;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.Response;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.CommonUtils;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component("coinswitchx_getOrders")
@RequiredArgsConstructor
public class GetOrders implements BaseHandler {
    Logger logger = LoggerFactory.getLogger(GetOrderDetailsHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    private final OrderMapper orderMapper;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    public <K,V> void process(HandlerContext<K,V>  handlerContext) throws Exception {


        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        String path = getRequestString((Map<String, Object>) handlerContext.getData());
        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.GET.name(),
                path, new HashMap<>(), new HashMap<>());
        Response<List<CoinSwitchOrderResponse>> response = null;
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
            logger.error("Exception in getting Order Details for   path {}", path);
            throw new Exception("Exception in getting Order Details for path " + path);
        }
        handlerContext.setOrderResponseList(orderMapper.toOrderResponse(response.getData()));
    }

    private String getRequestString(Map<String, Object> data) {
        String path = getPath();
        if (validateCount(data.get("count"))) {
            path = path.concat("count=" + URLEncoder.encode((String) data.get("count"), StandardCharsets.UTF_8))
                    .concat("&");
        }
        if (validateFromTimeAndToTime(data.get("fromTime"), data.get("toTime"))) {
            path = path.concat("from_time=" + URLEncoder.encode((String) data.get("fromTime"), StandardCharsets.UTF_8))
                    .concat("&");
            path = path.concat("to_time=" + URLEncoder.encode((String) data.get("toTime"), StandardCharsets.UTF_8))
                    .concat("&");
        }
        if (validateSide(data.get("side"))) {
            path = path.concat("side=" + URLEncoder.encode((String) data.get("side"), StandardCharsets.UTF_8))
                    .concat("&");
        }
        if (validateSymbols(data.get("symbols"))) {
            path = path.concat("symbols=" + URLEncoder.encode((String) data.get("symbols"), StandardCharsets.UTF_8))
                    .concat("&");
        }
        if (validateType(data.get("type"))) {
            path = path.concat("type=" + URLEncoder.encode((String) data.get("type"), StandardCharsets.UTF_8))
                    .concat("&");
        }
        if (validateStatus(data.get("status"))) {
            path = path.concat("open=" + URLEncoder.encode((String) data.get("status"), StandardCharsets.UTF_8))
                    .concat("&");
        }
        path = path.concat("exchange=" + URLEncoder.encode((String) data.get("exchange"), StandardCharsets.UTF_8));
        return path;
    }

    private boolean validateStatus(Object status) {
        return Objects.nonNull(status)
                && CommonUtils.getOptional(() -> OrderStatus.in((String) status)).filter(val -> val).isPresent();
    }

    private boolean validateType(Object type) {
        return Objects.nonNull(type) && CommonUtils.getOptional(() -> "LIMIT".equalsIgnoreCase((String) type)).filter(val -> val)
                .isPresent();
    }

    private boolean validateSymbols(Object symbols) {
        return Objects.nonNull(symbols);
    }

    private boolean validateSide(Object side) {
        return Objects.nonNull(side) && CommonUtils.getOptional(() -> Side.in((String) side)).filter(val -> val)
                .isPresent();
    }

    private boolean validateFromTimeAndToTime(Object fromTime, Object toTime) {
        return Objects.nonNull(fromTime) && Objects.nonNull(toTime) && CommonUtils
                .getOptional(() -> Long.parseLong((String) fromTime) > Long.parseLong((String) toTime))
                .filter(val -> val).isPresent();
    }

    private boolean validateCount(Object count) {
        return CommonUtils.getOptional(() -> Integer.valueOf((String) count)).isPresent();
    }

    private String getPath() {
        return "/trade/api/v2/orders";
    }
}
