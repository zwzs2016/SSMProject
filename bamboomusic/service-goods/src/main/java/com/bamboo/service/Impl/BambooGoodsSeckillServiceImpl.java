package com.bamboo.service.Impl;

import com.bamboo.mapper.BambooGoodsSeckillMapper;
import com.bamboo.service.BambooGoodsSeckillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uwan.common.constant.RedisConstants;
import com.uwan.common.dto.GoodsSeckillDTO;
import com.uwan.common.entity.BambooGoodsSeckill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BambooGoodsSeckillServiceImpl extends ServiceImpl<BambooGoodsSeckillMapper, BambooGoodsSeckill> implements BambooGoodsSeckillService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public boolean addSeckill(GoodsSeckillDTO goodsSeckillDTO) {
        BambooGoodsSeckill bambooGoodsSeckill=new BambooGoodsSeckill();
        bambooGoodsSeckill.setGoodsId(goodsSeckillDTO.getGoodsId());
        bambooGoodsSeckill.setUserId(goodsSeckillDTO.getUserId());
        bambooGoodsSeckill.setStock(goodsSeckillDTO.getStock());
        bambooGoodsSeckill.setSeckillStartTime(goodsSeckillDTO.getSeckillStartTime());
        bambooGoodsSeckill.setSeckillEndTime(goodsSeckillDTO.getSeckillEndTime());

        //存入db
        boolean save = save(bambooGoodsSeckill);

        if (save){
            //保存到redis
            stringRedisTemplate.opsForValue().set(RedisConstants.SECKILL_STOCK_KEY.getMsg()+goodsSeckillDTO.getGoodsId().toString(), String.valueOf(goodsSeckillDTO.getStock()));
            return true;
        }

        return false;
    }
}
