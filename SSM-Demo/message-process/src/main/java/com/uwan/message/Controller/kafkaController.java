package com.uwan.message.Controller;

import com.uwan.common.entity.out.ResponseEntityResult;
import com.uwan.message.dto.KafkaSendMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
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
        kafkaTemplate.send(kafkaSendMessageDTO.getTopic(), kafkaSendMessageDTO.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseEntityResult(String.valueOf(HttpStatus.OK.value()),"kafka消息发送成功",null,true));
    }
}
