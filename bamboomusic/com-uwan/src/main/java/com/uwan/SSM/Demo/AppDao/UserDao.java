package com.uwan.SSM.Demo.AppDao;

import com.uwan.SSM.Demo.AppEntity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll(int id);
}
