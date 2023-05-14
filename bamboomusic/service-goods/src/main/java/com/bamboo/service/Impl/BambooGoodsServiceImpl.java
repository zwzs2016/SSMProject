package com.bamboo.service.Impl;

import com.bamboo.mapper.BambooGoodsMapper;
import com.bamboo.service.BambooGoodsService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uwan.common.constant.SqlExecuteStatus;
import com.uwan.common.entity.BambooGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BambooGoodsServiceImpl extends ServiceImpl<BambooGoodsMapper, BambooGoods> implements BambooGoodsService {
    @Override
    @Transactional
    public int stockReduce(Long goodsId) {
        BambooGoods bambooGoods=new BambooGoods();
        UpdateWrapper<BambooGoods> wrapper=new UpdateWrapper<>();
        wrapper.eq("id",goodsId);
        wrapper.setSql("`stock`=`stock`-1");

        boolean update = update(wrapper);

//        if (goodsId.equals(1648698976644063233L)){
//            throw new RuntimeException("库存减少失败");
//        }

        if (update){
            return SqlExecuteStatus.INSERT_SUCCESS.getValue();
        }
        return SqlExecuteStatus.INSERT_FAIL.getValue();
    }
}
