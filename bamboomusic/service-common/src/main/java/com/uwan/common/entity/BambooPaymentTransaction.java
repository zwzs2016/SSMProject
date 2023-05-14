package com.uwan.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bamboo_payment_transaction")
@ApiModel("支付记录表")
public class BambooPaymentTransaction {
    @TableId
    private Long id;

    @TableField("pay_amount")
    private BigDecimal payAmount;

    @TableField("user_id")
    private Long userId;

    @TableField("order_id")
    private Long orderId;

    @TableField("revision")
    private int revision;

    @TableField("partypay_id")
    private Long partypayId;

    @TableField("payment_id")
    private Long paymentId;

    @TableField("product_name")
    private String productName;

    @TableField("created_by")
    private String createdBy;

    @TableField("updated_by")
    private String updatedBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
