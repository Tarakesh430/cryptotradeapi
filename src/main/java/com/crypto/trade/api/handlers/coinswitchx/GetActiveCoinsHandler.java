package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.Response;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component("coinswitchx_getActiveCoins")
@RequiredArgsConstructor
public class GetActiveCoinsHandler implements BaseHandler {
    private final Logger logger = LoggerFactory.getLogger(GetActiveCoinsHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    @Override
    public <V> void process(HandlerContext<V> handlerContext) throws Exception {
        logger.info("Get Active Coins for Exchange {}", handlerContext.getExchange());

        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        String path = getPath();
        path = path.concat("?exchange=" + URLEncoder.encode(handlerContext.getExchange(), StandardCharsets.UTF_8));

        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.GET.name(),
                path, new HashMap<>(), new HashMap<>());
        Response<Map<String, List<String>>> response = null;
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
            logger.error("Exception in Getting Active Coins for exchange {} with path {}", handlerContext.getExchange(), path);
            throw new Exception("Exception in Getting Active Coins for exchange " + handlerContext.getExchange());
        }
        logger.info("Successfully retrieved Actibve Coinst for exchange {} path{}", handlerContext.getExchange(), path);
        logger.info("Active Coins Fetched {}", response);
        //Populate the Handler Context
        handlerContext.setCoins(response.getData().get(handlerContext.getExchange()));
    }

    @Override
    public String getPath() {
        return "/trade/api/v2/coins";
    }
}
