package com.bamboo.service;

import com.bamboo.entity.BambooMusicInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.SQLException;

public interface BambooMusicInfoService extends IService<BambooMusicInfo> {
    Page<BambooMusicInfo> queryBambooMusic(String search, String page);

    int saveToRedis(Page<BambooMusicInfo> bambooMusicInfoPage,String key);

    Page<BambooMusicInfo> queryFormRedis(String name, String search, String page) throws Exception;

    void beforeInsertCheck(String author) throws SQLException;
}
