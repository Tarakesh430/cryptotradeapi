package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.PortFolio;
import com.crypto.trade.api.response.Response;
import com.crypto.trade.api.response.coinswitch.CoinSwitchOrderResponse;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@Component("coinswitchx_getPortFolio")
@RequiredArgsConstructor
public class PortFolioHandler implements BaseHandler {
    private Logger logger = LoggerFactory.getLogger(PortFolioHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;

    public <K, V> void process(HandlerContext<K, V> handlerContext) throws Exception {
        logger.info("Get Port Folio Details");
        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.GET.name(),
                getPath(), new HashMap<>(), new HashMap<>());
        Response<List<PortFolio>> response = null;

        try {
            response = restClient.get().uri(getPath())
                    .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
                    .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
                    .retrieve().body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception ex) {
            logger.info("Exception ex {}", ex.getMessage());
        }
        if (response == null) {
            logger.error("Unable to retrieve the Port Folio Details", getPath());
            throw new RuntimeException("Exception in Retrieving Port Folio Details ");
        }
        logger.info("SuccessFully Retrieved Port Folio Details path {}", getPath());
        logger.info("Port Folio Details Fetched {}", response);
        handlerContext.setPortFolio(response.getData());
    }

    private String getPath() {
        return "/trade/api/v2/user/portfolio";
    }

}
