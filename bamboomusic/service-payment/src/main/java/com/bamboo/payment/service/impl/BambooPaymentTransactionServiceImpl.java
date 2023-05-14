package com.bamboo.payment.service.impl;

import com.bamboo.payment.service.feign.GoodsFeignService;
import com.uwan.common.constant.SqlExecuteStatus;
import com.uwan.common.dto.BambooPaymentTransactionDTO;
import com.uwan.common.entity.BambooPaymentTransaction;
import com.bamboo.payment.mapper.BambooPaymentTransactionMapper;
import com.bamboo.payment.service.BambooPaymentTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class BambooPaymentTransactionServiceImpl extends ServiceImpl<BambooPaymentTransactionMapper, BambooPaymentTransaction> implements BambooPaymentTransactionService {

    @Autowired
    GoodsFeignService goodsFeignService;


    @Override
    @Transactional
    @GlobalTransactional(rollbackFor = Exception.class)
    public int addPaymentTransaction(BambooPaymentTransactionDTO bambooPaymentTransactionDTO) {
        log.info("分布式事务XID:"+ RootContext.getXID());

        BambooPaymentTransaction bambooPaymentTransaction=new BambooPaymentTransaction();
        bambooPaymentTransaction.setPayAmount(bambooPaymentTransactionDTO.getPayAmount());
        bambooPaymentTransaction.setUserId(bambooPaymentTransactionDTO.getUserId());
        bambooPaymentTransaction.setOrderId(bambooPaymentTransactionDTO.getOrderId());
        boolean save = save(bambooPaymentTransaction);

        goodsFeignService.stockReduce(1648698976644063233L);

        if (bambooPaymentTransactionDTO.getPayAmount().equals(BigDecimal.valueOf(11.31))){
            throw new RuntimeException("[支付服务]>------>新增异常");
        }
        if (save){
            return SqlExecuteStatus.INSERT_SUCCESS.getValue();
        }
        return SqlExecuteStatus.INSERT_FAIL.getValue();
    }
}
