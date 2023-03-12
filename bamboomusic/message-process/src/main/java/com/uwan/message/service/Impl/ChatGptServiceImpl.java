package com.uwan.message.service.Impl;

import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import com.uwan.message.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatGptServiceImpl implements ChatGptService {
    @Autowired
    OpenAiClient openAiClient;

    @Override
    public CompletionResponse query(String requestText) {
        return openAiClient.completions(requestText);
    }
}
