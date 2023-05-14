package com.bamboo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uwan.common.entity.BambooGoods;

public interface BambooGoodsService extends IService<BambooGoods> {
    int stockReduce(Long goodsId);
}
