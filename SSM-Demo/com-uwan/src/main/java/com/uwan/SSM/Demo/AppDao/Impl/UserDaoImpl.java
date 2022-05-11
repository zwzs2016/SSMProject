package com.uwan.SSM.Demo.AppDao.Impl;

import com.uwan.SSM.Demo.AppDao.UserDao;
import com.uwan.SSM.Demo.AppEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<User> findAll(int id) {
        List<User> users= jdbcTemplate.query("select * from user where id<?",new Object[]{id},new BeanPropertyRowMapper<>(User.class));
        return users;
    }
}
