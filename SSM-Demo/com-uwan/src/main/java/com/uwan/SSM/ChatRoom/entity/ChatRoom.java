package com.uwan.SSM.ChatRoom.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("room_info")
public class ChatRoom {
    @TableId
    private Long roomId;
    private String roomName;
    private String roomCreatorName;
    private String roomCreatorId;
    private String roomMode;
    private String roomRemark;
    private String roomMaxNumPeople;
    private String roomOnlinePeople;
}
