package com.uwan.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BambooPaymentTransactionDTO {
    @NotNull
    private BigDecimal payAmount;

    @NotNull
    private Long userId;

    @NotNull
    private Long orderId;

    private String productName;
}
