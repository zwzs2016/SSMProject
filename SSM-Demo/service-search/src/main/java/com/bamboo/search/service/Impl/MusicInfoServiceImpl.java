package com.bamboo.search.service.Impl;

import com.bamboo.search.constant.request.ElasticsearchExecuteStatus;
import com.bamboo.search.dto.MusicInfoDTO;
import com.bamboo.search.entity.MusicInfo;
import com.bamboo.search.repository.MusicInfoRepository;
import com.bamboo.search.service.MusicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MusicInfoServiceImpl implements MusicInfoService {
    @Autowired
    MusicInfoRepository musicInfoRepository;

    @Override
    public int addMusicInfo(MusicInfoDTO musicInfoDTO) {
        MusicInfo musicInfo=new MusicInfo();
        musicInfo.setId(UUID.randomUUID().toString().replaceAll("\\-",""));
        musicInfo.setAuthor(musicInfoDTO.getAuthor());
        musicInfo.setTitle(musicInfoDTO.getTitle());
        musicInfo.setRemark(musicInfoDTO.getRemark());
        musicInfo.setRoomId(musicInfoDTO.getRoomId());
        MusicInfo musicInfoSave = musicInfoRepository.save(musicInfo);
        if (musicInfoSave!=null){
            return ElasticsearchExecuteStatus.INSERT_SUCCESS.getValue();
        }else {
            return ElasticsearchExecuteStatus.INSERT_FAIL.getValue();
        }
    }

    @Override
    public List<MusicInfo> query(String search) {
        List<MusicInfo> musicInfoList=null;
        if (!StringUtils.isEmpty(search)){
            musicInfoList=musicInfoRepository.findByTitleOrRoomIdOrAuthor(search,search,search);
        }
        return musicInfoList;
    }
}
