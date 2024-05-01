package com.crypto.trade.api.utils;

import com.crypto.trade.api.utils.constants.CommonConstants;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CommonUtils {
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
}
