package com.uwan.SSM.Demo.AppService;

import com.uwan.SSM.Demo.AppEntity.User;

import java.util.List;


public interface UserService {
    List<User> getUserList(int id);
}
