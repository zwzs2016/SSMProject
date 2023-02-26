package com.uwan.message.controller;

import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import com.uwan.message.service.ChatGptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatgpt")
public class ChatGPTController {
    @Autowired
    ChatGptService chatGptService;

    @GetMapping("/question/{requestText}")
    public ResponseEntity chatQuestion(@PathVariable(name = "requestText") String requestText){
        if (!StringUtils.isEmpty(requestText)){
            CompletionResponse completionResponse= chatGptService.query(requestText);
            if (completionResponse!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(completionResponse);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
}
