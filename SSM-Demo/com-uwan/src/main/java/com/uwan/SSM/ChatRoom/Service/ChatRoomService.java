package com.uwan.SSM.ChatRoom.Service;

import com.uwan.SSM.ChatRoom.entity.ChatRoom;
import com.uwan.SSM.ChatRoom.vo.CrateRoomVo;

public interface ChatRoomService {
    public int insert(CrateRoomVo crateRoomVo);
    public ChatRoom selectAll();
}
