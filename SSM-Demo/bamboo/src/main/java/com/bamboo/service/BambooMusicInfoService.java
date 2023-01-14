package com.bamboo.service;

import com.bamboo.dto.BambooMusicInfoDTO;
import com.bamboo.entity.BambooMusicInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.uwan.common.entity.MusicInfo;

import java.sql.SQLException;

public interface BambooMusicInfoService extends IService<BambooMusicInfo> {
    Page<BambooMusicInfo> queryBambooMusic(String search, String page);

    int saveToRedis(Page bambooMusicInfoPage,String key);

    Page<BambooMusicInfo> queryFormRedis(String name, String search, String page) throws Exception;

    void beforeInsertCheck(String author) throws SQLException;

    void addToElasticSearch(BambooMusicInfo bambooMusicInfo);

    Page<MusicInfo> queryFormElasticsearch(String search, String page);

    void delete(String username);
}
