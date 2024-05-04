package com.crypto.trade.api.validations;

import com.crypto.trade.api.exception.KeyValidationException;
import com.crypto.trade.api.request.KeyValidationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface KeyValidation {
     boolean validateKeys(KeyValidationRequest keyValidationRequest) throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException, KeyValidationException;
}
