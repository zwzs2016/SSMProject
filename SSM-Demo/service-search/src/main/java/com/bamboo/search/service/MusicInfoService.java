package com.bamboo.search.service;

import com.bamboo.search.dto.MusicInfoDTO;
import com.bamboo.search.entity.MusicInfo;

import java.util.List;

public interface MusicInfoService {
    int addMusicInfo(MusicInfoDTO musicInfoDTO);

    List<MusicInfo> query(String search);
}
