package com.bamboo.search.repository;

import com.bamboo.search.entity.MusicInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MusicInfoRepository extends ElasticsearchRepository<MusicInfo,Long> {

    List<MusicInfo> findByTitleOrRoomIdOrAuthor(String title,String roomId,String author);
}

