package com.bamboo.config;

import com.uwan.common.util.MyStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {
    /**
     * 加解密盐值
     */
    @Value("${jasypt.encryptor.password}")
    private String password;

    // @Bean("jasyptStringEncryptor")
    @Bean("myStringEncryptor")
    public StringEncryptor myStringEncryptor() {
        return new MyStringEncryptor(password);
    }
}
