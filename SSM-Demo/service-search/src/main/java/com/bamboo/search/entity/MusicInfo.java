package com.bamboo.search.entity;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "musicInfo",type = "docs", shards = 1, replicas = 0)
public class MusicInfo {
    private String title;

    private String imageBase64File;

    private String roomId;

    private String remark;

    private String author;
}
