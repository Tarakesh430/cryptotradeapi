package com.crypto.trade.api.handlers;

import com.crypto.trade.api.config.BeanLoader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LoadHandlerHelper {
    private final Logger logger = LoggerFactory.getLogger(LoadHandlerHelper.class);
    private final BeanLoader beanLoader;
    private final static String beanLoadString ="%s_%s";

    public Object loadHandlerBean(String exchange,String operation) throws Exception {
        logger.info("Load Bean for Exchange {} Operation {}",exchange,operation);
        Object bean = beanLoader.loadBean(String.format(beanLoadString, exchange, operation));
        if(Objects.isNull(bean)){
            logger.warn("No Handler Picked for  Exchange {} Operation {}",exchange,operation);
            throw new Exception("Invalid Properties passed to the Bean Loader. No Handler Available to perform Operation");
        }
        logger.info("Loaded Bean Successfully {}",bean.getClass());
        return bean;
    }
}
