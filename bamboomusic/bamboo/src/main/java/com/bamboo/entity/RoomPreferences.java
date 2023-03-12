package com.bamboo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("room_preferences")
public class RoomPreferences implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private Long userId;

    @TableField("room_id")
    private Integer roomId;

    @TableField("preference")
    private Float preference;
}
