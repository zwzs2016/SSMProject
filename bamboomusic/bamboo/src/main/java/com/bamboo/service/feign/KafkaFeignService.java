package com.bamboo.service.feign;

import com.uwan.common.dto.KafkaSendMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "message-process-server",path = "kafka")
public interface KafkaFeignService {
    @PostMapping("sendmessage")
    public ResponseEntity sendMessage(@RequestBody KafkaSendMessageDTO kafkaSendMessageDTO);
}
