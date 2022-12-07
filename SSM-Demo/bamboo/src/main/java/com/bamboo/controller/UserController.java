package com.bamboo.controller;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.dto.UserDTO;
import com.bamboo.entity.User;
import com.bamboo.mapper.UserMapper;
import com.bamboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.selectList(null));
    }

    @PostMapping("addUser")
    public ResponseEntity addUser(@Valid @RequestBody UserDTO userDTO){
        if (userDTO.getPassword().equals(userDTO.getRepassword())){
            User user=new User();
            user.setName(userDTO.getName());
            user.setAge(userDTO.getAge());
            user.setHeight(userDTO.getHeight());
            user.setPassword(userDTO.getPassword());
            if (SqlExecuteStatus.INSERT_SUCCESS.getValue()==userService.addUser(user)){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(SqlExecuteStatus.INSERT_SUCCESS.getMsg());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SqlExecuteStatus.INSERT_FAIL.getMsg());
    }
}
