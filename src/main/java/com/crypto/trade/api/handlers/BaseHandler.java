package com.crypto.trade.api.handlers;

import com.crypto.trade.api.request.HandlerContext;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

public interface BaseHandler {

    <K,V> void process(HandlerContext<K,V> handlerContext) throws Exception;

    String getPath();

}
