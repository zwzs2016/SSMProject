package com.bamboo.controller;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.entity.User;
import com.bamboo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("getAll")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.selectList(null));
    }

    @PostMapping("addUser")
    public ResponseEntity addUser(@RequestBody User user){
        if (SqlExecuteStatus.INSERT_SUCCESS.getValue()==userMapper.insert(user)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(SqlExecuteStatus.INSERT_SUCCESS.getMsg());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SqlExecuteStatus.INSERT_FAIL.getMsg());
    }
}
