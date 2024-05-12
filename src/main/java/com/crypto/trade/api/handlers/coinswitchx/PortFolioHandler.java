package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.handlers.BaseHandler;
import com.crypto.trade.api.request.HandlerContext;
import com.crypto.trade.api.response.PortFolioResponse;
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

import java.util.HashMap;
import java.util.List;

@Component("coinswitchx_getPortFolio")
@RequiredArgsConstructor
public class PortFolioHandler implements BaseHandler {
    private Logger logger = LoggerFactory.getLogger(PortFolioHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;
    @Value("${coinswitch.trade.api.baseUrl}")
    private String baseUrl;
    public <V> void process(HandlerContext<V> handlerContext) throws Exception {
        logger.info("Get Port Folio Details");
        HttpHeaders httpHeaders = handlerContext.getHttpHeaders();
        String secretKey = httpHeaders.getFirst(CommonConstants.SECRET_KEY_HEADER);
        String apiKey = httpHeaders.getFirst(CommonConstants.API_KEY_HEADER);

        String path = getPath();
        String signature = coinSwitchSignatureGeneration.generateSignature(secretKey, HttpMethod.GET.name(),
                path, new HashMap<>(), new HashMap<>());
        Response<List<PortFolioResponse>> response = null;

        try {
            response = restClient.get().uri(baseUrl.concat(path))
                    .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
                    .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
                    .retrieve().body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception ex) {
            logger.info("Exception ex {}", ex.getMessage());
        }
        if (response == null) {
            logger.error("Unable to retrieve the Port Folio Details");
            throw new Exception("Exception in Retrieving Port Folio Details ");
        }
        logger.info("SuccessFully Retrieved Port Folio Details path {}", getPath());
        logger.info("Port Folio Details Fetched {}", response);
        handlerContext.setPortFolio(response.getData());
    }

    @Override
    public String getPath() {
        return "/trade/api/v2/user/portfolio";
    }

}
