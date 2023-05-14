package com.bamboo.payment.controller;

import com.bamboo.payment.service.BambooPaymentTransactionService;
import com.uwan.common.constant.SqlExecuteStatus;
import com.uwan.common.dto.BambooPaymentTransactionDTO;
import com.uwan.common.entity.BambooPaymentTransaction;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
@Api("支付模块")
public class BambooPaymentController {
    @Autowired
    BambooPaymentTransactionService bambooPaymentTransactionService;

    @PostMapping("add")
    public ResponseEntity addPaymentTransaction(@RequestBody BambooPaymentTransactionDTO bambooPaymentTransactionDTO){
        int status = bambooPaymentTransactionService.addPaymentTransaction(bambooPaymentTransactionDTO);
        if (status == SqlExecuteStatus.INSERT_SUCCESS.getValue()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

}
