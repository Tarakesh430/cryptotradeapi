package com.crypto.trade.api.handlers;

import com.crypto.trade.api.request.HandlerContext;


public interface BaseHandler {

    <V> void process(HandlerContext<V> handlerContext) throws Exception;

    String getPath();

}
