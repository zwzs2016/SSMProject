package com.bamboo.controller;

import com.bamboo.entity.out.ResponseEntityResult;
import com.bamboo.service.feign.MusicInfoFeignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Api("test服务")
public class TestController {
    @Autowired
    MusicInfoFeignService musicInfoFeignService;

    @GetMapping("ribbon")
    public ResponseEntity ribbon(){
        ResponseEntity ribbon = musicInfoFeignService.ribbon();
//        ResponseEntityResult body = ribbon.getBody();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ribbon.getBody());
    }
}
