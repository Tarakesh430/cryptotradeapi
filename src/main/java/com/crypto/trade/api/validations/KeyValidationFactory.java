package com.crypto.trade.api.validations;

import com.crypto.trade.api.request.KeyValidationRequest;

public interface KeyValidationFactory {
    KeyValidation keyValidationFactory(String exchange);
}
