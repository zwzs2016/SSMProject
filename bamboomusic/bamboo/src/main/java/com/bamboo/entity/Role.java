package com.bamboo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class Role {
    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("role_name")
    private String roleName;

    @TableField("role_name_cn")
    private String roleNameCn;
}
