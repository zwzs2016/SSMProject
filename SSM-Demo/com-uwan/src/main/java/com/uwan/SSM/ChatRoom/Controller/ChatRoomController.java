package com.uwan.SSM.ChatRoom.Controller;

import com.uwan.SSM.ChatRoom.Service.ChatRoomService;
import com.uwan.SSM.ChatRoom.vo.CrateRoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class ChatRoomController {
    @Autowired
    ChatRoomService chatRoomService;

    @PostMapping("/crateRoom")
    public String crateRoom(@RequestBody CrateRoomVo crateRoomVo){
        int res = chatRoomService.insert(crateRoomVo);
        if (res==0){
            return "0";
        }
        return "1";
    }
}
