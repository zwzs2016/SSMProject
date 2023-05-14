package com.bamboo.controller;

import com.bamboo.service.BambooGoodsOrderService;
import com.uwan.common.dto.GoodsOrderDTO;
import com.uwan.common.entity.BambooGoodsOrder;
import com.uwan.common.entity.out.ResponseEntityResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/goodorder")
@Api("商品订单")
public class GoodsOrderController {
    @Autowired
    BambooGoodsOrderService bambooGoodsOrderService;

    @ApiOperation("商品订单添加")
    @PostMapping("/add")
    public ResponseEntity goodsOrderAdd(@RequestBody @Valid GoodsOrderDTO goodsOrderDTO){
        Long userId=goodsOrderDTO.getUserId();
        Long goodsId=goodsOrderDTO.getGoodsId();

        String result = bambooGoodsOrderService.seckillGoods(goodsId, userId);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

}
