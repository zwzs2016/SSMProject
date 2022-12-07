package com.bamboo.config;

import com.bamboo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean
    public User user(){
        return new User();
    }
}
