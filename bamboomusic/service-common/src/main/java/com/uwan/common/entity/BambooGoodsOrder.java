package com.uwan.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bamboo_goods_order")
public class BambooGoodsOrder {
    @TableId
    private Long id;

    @TableField("goods_id")
    private Long goodsId;

    @TableField("user_id")
    private Long userId;

    @TableField("pay_count")
    private int payCount;

    @TableField("address")
    private String address;

    @TableField("is_pay_success")
    private Boolean isPaySuccess;

    @TableField("is_pay_overdue")
    private Boolean isPayOverdue;

    @TableField("is_delete")
    @TableLogic(value = "0",delval = "1")
    private Boolean isDelete;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
