package com.bamboo.controller;

import com.bamboo.service.BambooGoodsService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.uwan.common.constant.SqlExecuteStatus;
import com.uwan.common.dto.GoodsDTO;
import com.uwan.common.entity.BambooGoods;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//swagger
@Api("商品服务")
@RestController
//@RequestMapping("/goods-service")
public class GoodsController {
    @Autowired
    BambooGoodsService bambooGoodsService;

    @Operation(summary = "添加商品信息")
    @PostMapping("/addgoods")
    public ResponseEntity addGoods(@Valid @RequestBody GoodsDTO goodsDto){
        //test...
        BambooGoods goods=new BambooGoods();
        goods.setName(goodsDto.getName());
        goods.setItemPrice(goodsDto.getItemPrice());
        goods.setStock(goodsDto.getStock());
        goods.setExtraInfo(goodsDto.getExtraInfo());
        boolean save = bambooGoodsService.save(goods);
        if (save){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);

    }

    @Operation(summary = "商品库存信息变化")
    @PostMapping("/stockreduce")
    public ResponseEntity stockReduce(@NotEmpty Long goodsId){
        int status=bambooGoodsService.stockReduce(goodsId);

        if (status== SqlExecuteStatus.INSERT_SUCCESS.getValue()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
}
