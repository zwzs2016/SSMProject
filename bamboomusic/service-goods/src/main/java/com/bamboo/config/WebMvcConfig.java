package com.bamboo.config;

import io.seata.integration.http.TransactionPropagationIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new TransactionPropagationIntercepter());
        super.addInterceptors(registry);
    }
}
