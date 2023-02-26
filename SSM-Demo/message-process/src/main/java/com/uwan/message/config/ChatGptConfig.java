package com.uwan.message.config;

import com.unfbx.chatgpt.OpenAiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatGptConfig {
    @Value("${custom.openai.apikey}")//connectTimeout writeTimeout readTimeout
    private String apiKey;

    @Value("${custom.openai.connectTimeout}")
    private long connectTimeout;

    @Value("${custom.openai.writeTimeout}")
    private long writeTimeout;

    @Value("${custom.openai.readTimeout}")
    private long readTimeout;

    @Bean
    public OpenAiClient openAiClient(){
        return new OpenAiClient(apiKey,connectTimeout,writeTimeout,readTimeout);
    }
}
