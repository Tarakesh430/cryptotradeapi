package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.mapper.OrderMapper;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.DepthDetailsResponse;
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
import java.util.Objects;

@Component("coinswitchx_getDepthDetails")
@RequiredArgsConstructor
public class GetDepthDetailsHandler implements BaseHandler {
    Logger logger = LoggerFactory.getLogger(GetDepthDetailsHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    private final OrderMapper orderMapper;

    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;

    @Override
    public <V> void process(HandlerContext<V> handlerContext) throws Exception {

        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        logger.info("Get Depth Details for Symbol {} exchange {}", handlerContext.getCoins().getFirst(),
                handlerContext.getExchange());
        String path = getPath();
        path = path.concat("?exchange=" + URLEncoder.encode(handlerContext.getExchange(), StandardCharsets.UTF_8))
                .concat("&symbol=" + URLEncoder.encode(handlerContext.getCoins().getFirst(), StandardCharsets.UTF_8));
        path = path.replaceAll("%2F", "/");
        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.GET.name(),
                path, new HashMap<>(), new HashMap<>());
        Response<DepthDetailsResponse> response = null;
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
            logger.error("Exception in getting Depth Details for Exchange {}  Symbol {} path {}",
                    handlerContext.getExchange(), handlerContext.getCoins().getFirst(), path);
            throw new Exception("Exception in getting Depth Details for Symbol  " + handlerContext.getCoins().getFirst());
        }
        handlerContext.setDepthDetailsResponse(response.getData());
    }

    @Override
    public String getPath() {
        return "/trade/api/v2/depth";
    }
}
