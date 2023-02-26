package com.bamboo.search.repository;

import com.uwan.common.entity.MusicInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MusicInfoRepository extends ElasticsearchRepository<MusicInfo,Long> {

    List<MusicInfo> queryByTitleOrRoomIdOrAuthor(String title, String roomId, String author, Pageable pageable);

    int deleteByAuthor(String author);
}

