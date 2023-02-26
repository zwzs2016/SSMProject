package com.uwan.message.service;

import com.unfbx.chatgpt.entity.completions.CompletionResponse;

public interface ChatGptService {
    CompletionResponse query(String requestText);
}
