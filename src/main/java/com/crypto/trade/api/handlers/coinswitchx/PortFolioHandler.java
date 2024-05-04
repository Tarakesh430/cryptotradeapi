package com.crypto.trade.api.handlers.coinswitchx;

import com.crypto.trade.api.response.PortFolio;
import com.crypto.trade.api.security.SignatureGeneration;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@Component("coinswitchx_portFolioHandler")
@RequiredArgsConstructor
public class PortFolioHandler {
    private Logger logger = LoggerFactory.getLogger(PortFolioHandler.class);

    private final RestClient restClient;
    private final SignatureGeneration coinSwitchSignatureGeneration;

    public List<PortFolio> getPortFolioDetails() throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
//        logger.info("Get Port Folio Details");
//        String signature = coinSwitchSignatureGeneration.generateSignature(HttpMethod.GET.name(),
//                getPath(), new HashMap<>(), new HashMap<>());
//        CoinSwitchResponse<List<PortFolio>> coinSwitchResponse = restClient.get().uri(getPath())
//                .header(CommonConstants.CS_AUTH_SIGNATURE, signature)
//                .header(CommonConstants.CS_AUTH_APIKEY, apiKey)
//                .retrieve().body(new ParameterizedTypeReference<>() {
//                });
//        if (coinSwitchResponse == null) {
//            logger.error("Unable to retrieve the Port Folio Details", getPath());
//            throw new RuntimeException("Exception in Retrieving Port Folio Details " );
//        }
//        logger.info("SuccessFully Retreived Port Folio Details path {}", getPath());
//        logger.info("Port Folio Details Fetched {}", coinSwitchResponse);
//        return coinSwitchResponse;
        return null;
    }

    private String getPath() {
        return "/trade/api/v2/user/portfolio";
    }
}
