package com.bamboo.util.handler;

import com.bamboo.constant.request.RedisExecuteStatus;
import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisBusyException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义注销成功之后处理
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    BambooMusicInfoService bambooMusicInfoService;

    @Autowired
    UserService userService;

    @SneakyThrows
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws  IOException {
        //delete 电台信息
        QueryWrapper<BambooMusicInfo> queryWrapper= Wrappers.query();
        queryWrapper.eq("author",authentication.getName());
        int shutdown = userService.shutdown(authentication.getName());
        boolean remove = bambooMusicInfoService.remove(queryWrapper);

        if (shutdown== RedisExecuteStatus.DELETE_FAIL.getValue()){
            throw new RedisBusyException(RedisExecuteStatus.DELETE_FAIL.getMsg());
        }

        if (!remove){
            throw new SQLException(SqlExecuteStatus.DELETE_FAIL.getMsg());
        }

        response.sendRedirect("/");


    }
}