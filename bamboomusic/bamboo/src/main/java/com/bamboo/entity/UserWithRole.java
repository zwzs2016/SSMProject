package com.bamboo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithRole {
    private static final long serialVersionUID=1L;

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Long userId;

    @TableField("role_id")
    private Integer roleId;
}
