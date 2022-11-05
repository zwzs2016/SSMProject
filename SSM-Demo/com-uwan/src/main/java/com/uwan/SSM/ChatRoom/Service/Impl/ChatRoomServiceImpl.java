package com.uwan.SSM.ChatRoom.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uwan.SSM.ChatRoom.dao.ChatRoomMapper;
import com.uwan.SSM.ChatRoom.Service.ChatRoomService;
import com.uwan.SSM.ChatRoom.entity.ChatRoom;
import com.uwan.SSM.ChatRoom.vo.CrateRoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Transactional
@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    ChatRoomMapper chatRoomMapper;

    public ChatRoom selectAll(){
        QueryWrapper<ChatRoom> wrapper=new QueryWrapper<>();
        List<ChatRoom> chatRooms = chatRoomMapper.selectList(wrapper);
        return new ChatRoom();
    }

    public int insert(@RequestBody CrateRoomVo crateRoomVo){
        ChatRoom chatRoom=new ChatRoom();
        chatRoom.setRoomName(crateRoomVo.getCreateRoomName());
        chatRoom.setRoomMode(crateRoomVo.getRoomMode());
        chatRoom.setRoomRemark(crateRoomVo.getRoomRemark());
        return chatRoomMapper.insert(chatRoom);
    }
}
