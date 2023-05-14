package com.bamboo.controller;

import com.bamboo.service.BambooGoodsSeckillService;
import com.uwan.common.dto.GoodsSeckillDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@RequestMapping("goos-service")
@Api("商品优惠券添加")
public class GoodsSeckillController {
    @Autowired
    BambooGoodsSeckillService bambooGoodsSeckillService;

    @PostMapping("/addgoodsseckill")
    public ResponseEntity addGoodsSeckill(@Valid @RequestBody GoodsSeckillDTO goodsSeckillDTO){
        boolean save = bambooGoodsSeckillService.addSeckill(goodsSeckillDTO);

        if (save){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

}
