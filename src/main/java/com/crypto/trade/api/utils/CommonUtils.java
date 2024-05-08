package com.crypto.trade.api.utils;

import com.crypto.trade.api.utils.constants.CommonConstants;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static String paramsToString(Map<String, String> params) {
        // Convert query params to string

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(CommonConstants.AMPERSAND_MARK);
            }
            stringBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
        }
        return stringBuilder.toString();
    }
    public<T> Optional<T> getOptional(Supplier<T> resolver){
        try{
            return Optional.ofNullable(resolver.get());
        }catch(Exception ex){
            logger.info("Exception Occurred in Resolver {}",ex.getMessage());
        }
        return Optional.empty();
    }
}
