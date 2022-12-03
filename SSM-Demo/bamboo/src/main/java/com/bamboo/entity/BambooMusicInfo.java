package com.bamboo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bamboo_music_info")
public class BambooMusicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("author")
    private String author;

    @TableField("remarks")
    private String remarks;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
