package com.bamboo.service;

import com.bamboo.dto.BambooMusicInfoDTO;
import com.bamboo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends IService<User>, UserDetailsService {
    int addUser(User user);

    int shutdown(String username);

    int saveToMusicInfo(BambooMusicInfoDTO bambooMusicInfoDTO);
}
