package com.bamboo.payment.service;

import com.uwan.common.dto.BambooPaymentTransactionDTO;
import com.uwan.common.entity.BambooPaymentTransaction;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BambooPaymentTransactionService extends IService<BambooPaymentTransaction> {
    int addPaymentTransaction(BambooPaymentTransactionDTO bambooPaymentTransactionDTO);
}
