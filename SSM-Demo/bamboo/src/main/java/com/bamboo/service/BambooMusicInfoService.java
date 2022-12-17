package com.bamboo.service;

import com.bamboo.entity.BambooMusicInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BambooMusicInfoService extends IService<BambooMusicInfo> {
    Page<BambooMusicInfo> queryBambooMusic(String search, String page);

    int saveToRedis(Page<BambooMusicInfo> bambooMusicInfoPage,String key);

    Page<BambooMusicInfo> queryFormRedis(String key) throws Exception;
}
