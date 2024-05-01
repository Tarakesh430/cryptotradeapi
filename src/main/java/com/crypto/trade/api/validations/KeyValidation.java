package com.crypto.trade.api.validations;

import com.crypto.trade.api.exception.KeyValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface KeyValidation {
    public boolean validateKeys() throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException, KeyValidationException;
}
