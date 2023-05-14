package com.uwan.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bamboo_goods")
public class BambooGoods {
    @TableId
    private Long id;

    @TableField("name")
    private String name;

    @TableField("is_ground")
    private Boolean isGround;

    @TableField("item_price")
    private BigDecimal itemPrice;

    @TableField("extra_info")
    private String extraInfo;

    @TableField("stock")
    private int stock;

    @TableField("item_img")
    private byte[] itemImg;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
