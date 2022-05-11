package com.uwan.SSM.Demo.AppService.Impl;

import com.uwan.SSM.Demo.AppDao.UserDao;
import com.uwan.SSM.Demo.AppEntity.User;
import com.uwan.SSM.Demo.AppService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUserList(int id) {
        return userDao.findAll(id);
    }
}
