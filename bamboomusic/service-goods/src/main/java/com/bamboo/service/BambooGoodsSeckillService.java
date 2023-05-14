package com.bamboo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uwan.common.dto.GoodsSeckillDTO;
import com.uwan.common.entity.BambooGoodsSeckill;

public interface BambooGoodsSeckillService extends IService<BambooGoodsSeckill> {

    boolean addSeckill(GoodsSeckillDTO goodsSeckillDTO);
}
