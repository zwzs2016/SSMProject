package com.uwan.SSM.AppService.Impl;

import com.uwan.SSM.AppDao.UserDao;
import com.uwan.SSM.AppEntity.User;
import com.uwan.SSM.AppService.UserService;
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
