package com.crypto.trade.api.keystrategy.impl;

import com.crypto.trade.api.keystrategy.SignatureRequestStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component("ed25519SignatureRequestStrategy")
@RequiredArgsConstructor
public class Ed25519SignatureRequestStrategy implements SignatureRequestStrategy {
    private final Logger logger = LoggerFactory.getLogger(Ed25519SignatureRequestStrategy.class);


    @Override
    public String generate(String request,String secretKey) {
        byte[] requestBytes = request.getBytes(StandardCharsets.UTF_8);

        // Decode secret key and initialize private key
        byte[] secretKeyBytes = Hex.decode(secretKey);
        Ed25519PrivateKeyParameters privateKey = new Ed25519PrivateKeyParameters(secretKeyBytes, 0);

        // Initialize signer and generate signature
        Ed25519Signer signer = new Ed25519Signer();
        signer.init(true, privateKey);
        signer.update(requestBytes, 0, requestBytes.length);
        byte[] signatureBytes = signer.generateSignature();

        // Convert signature bytes to hexadecimal string
        return Hex.toHexString(signatureBytes);
    }

}
