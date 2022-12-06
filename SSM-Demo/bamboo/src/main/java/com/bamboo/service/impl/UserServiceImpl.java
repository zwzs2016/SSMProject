package com.bamboo.service.impl;

import com.bamboo.entity.Role;
import com.bamboo.entity.User;
import com.bamboo.entity.UserWithRole;
import com.bamboo.mapper.UserMapper;
import com.bamboo.mapper.UserWithRoleMapper;
import com.bamboo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserWithRoleMapper userWithRoleMapper;

    @Autowired
    StringEncryptor stringEncryptor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> userWrapper=Wrappers.query();
        userWrapper.eq("name",username);
        User user = userMapper.selectOne(userWrapper);
        if(user == null){
            throw new UsernameNotFoundException("账户不存在！");
        }
        return user;
    }

    public String addUserByUsername(User user){
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("name",user.getUsername());
        User newuser = userMapper.selectOne(queryWrapper);
        if (newuser != null){
            return "账户存在，注册失败！";
        }else {
            //新用户密码采用BCryptPasswordEncoder(10)格式存入数据库
            User userRegister=new User();
            userRegister.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            //设置用户状态可用，没有锁定
            userRegister.setEnabled(true);
            userRegister.setLocked(false);
            //执行用户注册
            int adduser = userMapper.insert(userRegister);
            //用户成功注册后，添加用户角色
            if(adduser > 0){
                User getuser =userMapper.selectOne(queryWrapper);
                //创建关联关系
                UserWithRole userWithRole=new UserWithRole();
                userWithRole.setUserId(getuser.getId());
                //暂且默认普通用户...
                int adduserWithRole = userWithRoleMapper.insert(userWithRole);
                if (adduserWithRole > 0){
                    return "账户注册成功，角色注册成功！";
                }else{
                    return "账户注册成功！角色注册失败！";
                }
            }else {
                return "账户注册失败！";
            }
        }
    }
}
