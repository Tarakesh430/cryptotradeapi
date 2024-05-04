package com.crypto.trade.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanLoader {
    private final ApplicationContext applicationContext;

    @Autowired
    public BeanLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Object loadBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
