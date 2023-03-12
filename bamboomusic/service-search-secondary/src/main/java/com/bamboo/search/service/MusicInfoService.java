package com.bamboo.search.service;

import com.uwan.common.dto.MusicInfoDTO;
import com.uwan.common.entity.MusicInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MusicInfoService {
    int addMusicInfo(MusicInfoDTO musicInfoDTO);

    List<MusicInfo> query(String search, String page);

    int delete(String author);
}
