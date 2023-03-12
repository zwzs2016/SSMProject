package com.bamboo.controller;

import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.entity.User;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.uwan.common.util.JwtTokenUtils;
import com.uwan.common.util.UrlUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("live")
@Api("Live 权限鉴定")
public class LiveAuthentication {
    @Autowired
    UserService userService;

    @Autowired
    BambooMusicInfoService bambooMusicInfoService;

    @Qualifier("myStringEncryptor")
    @Autowired
    StringEncryptor stringEncryptor;

    @Operation(summary = "live推流鉴权")
    @RequestMapping("authentication")
    public ResponseEntity authentication(HttpServletRequest request){
        String tcurl = request.getParameter("tcurl");
        String livekey=org.apache.commons.lang3.StringUtils.substringAfter(tcurl, "?livekey=");
        String name = JwtTokenUtils.getUsername(livekey);
        String username = stringEncryptor.decrypt(name);
        if (!StringUtils.isEmpty(username)){
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            QueryWrapper<User> userQueryWrapper= Wrappers.query();
            userQueryWrapper.eq("name",username);
            userQueryWrapper.select("id,locked");
            User user = userService.getOne(userQueryWrapper);

            //是否有电台信息
            QueryWrapper<BambooMusicInfo> bambooMusicInfoQueryWrapper=Wrappers.query();
            bambooMusicInfoQueryWrapper.eq("author",username);
            long count = bambooMusicInfoService.count(bambooMusicInfoQueryWrapper);
            if (user!=null && count==1){
                //校验密码 账号情况
                if (!user.getLocked()){
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(null);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
}
