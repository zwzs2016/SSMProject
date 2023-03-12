package com.uwan.SSM.ChatRoom.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/CharRoom")
public class TestController {
    @GetMapping("/CharRoom/Test")
    public String test(){
        return "Welcome to the chat room";
    }
}
