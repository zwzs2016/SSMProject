package com.bamboo.service.feign;

import com.uwan.common.dto.BambooPaymentTransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(value = "payment-service",path = "payment")
public interface PaymentFeignService {
    @PostMapping("add")
    public ResponseEntity addPaymentTransaction(@RequestBody BambooPaymentTransactionDTO bambooPaymentTransactionDTO);
}
