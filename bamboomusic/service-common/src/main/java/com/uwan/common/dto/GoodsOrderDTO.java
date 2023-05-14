package com.uwan.common.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("秒杀订单DTO")
public class GoodsOrderDTO {
    @ApiModelProperty("商品Id")
    @NotNull
    private Long goodsId;

    @ApiModelProperty("用户Id")
    @NotNull
    private Long userId;
}
