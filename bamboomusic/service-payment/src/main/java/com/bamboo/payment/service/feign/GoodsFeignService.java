package com.bamboo.payment.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotEmpty;

@FeignClient(value = "goods-service")
public interface GoodsFeignService {
    @PostMapping("stockreduce")
    public ResponseEntity stockReduce(@NotEmpty Long goodsId);
}
