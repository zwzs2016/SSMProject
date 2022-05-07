package com.uwan.SSM.AppDao;

import com.uwan.SSM.AppEntity.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
}
