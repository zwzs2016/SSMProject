package com.uwan.message.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    //发送消息
    @GetMapping("/kafka/{message}")
    public void sendMessage(@PathVariable("message") String message){
        kafkaTemplate.send("wordsendertest",message);
    }
}
