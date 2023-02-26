package com.uwan.message.controller;

import com.google.gson.Gson;
import com.uwan.common.dto.KafkaSendMessageDTO;
import com.uwan.common.entity.out.ResponseEntityResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class kafkaController {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    //发送消息
    @PostMapping("sendmessage")
    public ResponseEntity sendMessage(@RequestBody @Validated KafkaSendMessageDTO kafkaSendMessageDTO){
        Gson gson=new Gson();
        kafkaTemplate.send(kafkaSendMessageDTO.getTopic(), gson.toJson(kafkaSendMessageDTO));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseEntityResult(String.valueOf(HttpStatus.OK.value()),"kafka消息发送成功",null,true));
    }
}
