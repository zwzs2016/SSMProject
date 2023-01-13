package com.bamboo.search.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "musicinfo")
public class MusicInfo {
    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text)
    private String roomId;

    @Field(type = FieldType.Keyword)
    private String remark;

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String author;
}
