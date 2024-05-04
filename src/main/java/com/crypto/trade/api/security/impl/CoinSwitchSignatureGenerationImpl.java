package com.crypto.trade.api.security.impl;

import com.crypto.trade.api.keystrategy.SignatureRequestStrategy;
import com.crypto.trade.api.security.SignatureGeneration;
import com.crypto.trade.api.utils.CoinSwitchCommonFunctions;
import com.crypto.trade.api.utils.CommonUtils;
import com.crypto.trade.api.utils.constants.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component("coinSwitchSignatureGeneration")
@RequiredArgsConstructor
public class CoinSwitchSignatureGenerationImpl implements SignatureGeneration {

    private final Logger logger = LoggerFactory.getLogger(CoinSwitchSignatureGenerationImpl.class);
    private final SignatureRequestStrategy signatureRequestStrategy;
    private final CoinSwitchCommonFunctions coinSwitchCommonFunctions;

    /**
     * Generates a signature for CoinSwitch requests.
     *
     * @param method   The HTTP method of the request.
     * @param endPoint The endpoint of the request.
     * @param payload  The payload of the request.
     * @param params   The query parameters of the request.
     * @return The generated signature.
     * @throws URISyntaxException      If there is an issue with the URI syntax.
     * @throws JsonProcessingException If there is an issue processing JSON.
     */
    @Override
    public String generateSignature(String secretKey, String method, String endPoint, Map<String, Object> payload, Map<String, String> params)
            throws URISyntaxException, JsonProcessingException {
        // Log the generation of signature
        logger.info("Generate Signature for Coinswitch Request method {}" +
                " for endpoint {} with Payload {} having Query Params {}", method, endPoint, payload, params);

        // Modify the endpoint for GET request if necessary
        if (method.equals(HttpMethod.GET.toString()) && !params.isEmpty()) {
            String query = new URI(endPoint).getQuery();
            endPoint += StringUtils.isBlank(query) ? CommonConstants.QUESTION_MARK : CommonConstants.AMPERSAND_MARK;
            endPoint += URLEncoder.encode(CommonUtils.paramsToString(params), StandardCharsets.UTF_8);
        }

        // Decode the endpoint
        endPoint = URLDecoder.decode(endPoint, StandardCharsets.UTF_8);

        // Create the message for signature
        String signatureMessage = coinSwitchCommonFunctions.signatureMessage(method, endPoint, payload);
        logger.info("Signature Message: {}", signatureMessage);

        // Generate the signature request
        String signatureRequest = signatureRequestStrategy.generate(signatureMessage, secretKey);
        logger.info("Signature Request: {}", signatureRequest);
        return signatureRequest;
    }

}
