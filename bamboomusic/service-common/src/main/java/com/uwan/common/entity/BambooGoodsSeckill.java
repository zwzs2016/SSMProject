package com.uwan.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bamboo_goods_seckill")
public class BambooGoodsSeckill {
    @MppMultiId
    @TableField("goods_id")
    private Long goodsId;

    @MppMultiId
    @TableField("user_id")
    private Long userId;

    @TableField("stock")
    private int stock;

    @TableField("seckill_start_time")
    private Date seckillStartTime;

    @TableField("seckill_end_time")
    private Date seckillEndTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
