package com.bamboo.vo;

import com.bamboo.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {
    private String username;
    private String livecode;
    private String token;
    private List<Role> roles;
}
