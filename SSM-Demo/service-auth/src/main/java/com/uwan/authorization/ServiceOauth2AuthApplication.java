package com.uwan.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
public class ServiceOauth2AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOauth2AuthApplication.class,args);
    }
}
