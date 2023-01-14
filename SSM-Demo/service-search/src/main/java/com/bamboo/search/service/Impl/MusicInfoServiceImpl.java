package com.bamboo.search.service.Impl;

import com.bamboo.search.constant.request.ElasticsearchExecuteStatus;
import com.bamboo.search.repository.MusicInfoRepository;
import com.bamboo.search.service.MusicInfoService;
import com.uwan.common.dto.MusicInfoDTO;
import com.uwan.common.entity.MusicInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MusicInfoServiceImpl implements MusicInfoService {
    @Autowired
    MusicInfoRepository musicInfoRepository;

    @Override
    public int addMusicInfo(MusicInfoDTO musicInfoDTO) {
        MusicInfo musicInfo=new MusicInfo();
        musicInfo.setId(musicInfoDTO.getId());
        musicInfo.setAuthor(musicInfoDTO.getAuthor());
        musicInfo.setTitle(musicInfoDTO.getTitle());
        musicInfo.setRemark(musicInfoDTO.getRemark());
        musicInfo.setRoomId(musicInfoDTO.getRoomId());
        musicInfo.setLiveUrl(musicInfoDTO.getLiveUrl());
        musicInfo.setImgFile(musicInfoDTO.getImgFile());
        MusicInfo musicInfoSave = musicInfoRepository.save(musicInfo);
        if (musicInfoSave!=null){
            return ElasticsearchExecuteStatus.INSERT_SUCCESS.getValue();
        }else {
            return ElasticsearchExecuteStatus.INSERT_FAIL.getValue();
        }
    }

    @Override
    public List<MusicInfo> query(String search, String page) {
        List<MusicInfo> musicInfoList=null;
        if (!StringUtils.isEmpty(search)){
            musicInfoList=musicInfoRepository.queryByTitleOrRoomIdOrAuthor(search,search,search, PageRequest.of(Integer.parseInt(page)-1, 9));
        }
        return musicInfoList;
    }

    @Override
    public int delete(String author) {
        int result = musicInfoRepository.deleteByAuthor(author);
        if (result==ElasticsearchExecuteStatus.DELETE_SUCCESS.getValue()){
            return ElasticsearchExecuteStatus.DELETE_SUCCESS.getValue();
        }
        return ElasticsearchExecuteStatus.DELETE_FAIL.getValue();
    }
}
