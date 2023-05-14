package com.bamboo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uwan.common.entity.BambooGoodsOrder;
import com.uwan.common.entity.BambooGoodsSeckill;

public interface BambooGoodsOrderService extends IService<BambooGoodsOrder> {
    String seckillGoods(Long goodsId,Long userId);

    void addSeckillGoods(BambooGoodsSeckill bambooGoodsSeckill);
}
