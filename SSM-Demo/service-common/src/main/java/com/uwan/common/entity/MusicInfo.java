package com.uwan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "musicinfo")
public class MusicInfo {
    private static final long serialVersionUID=1L;

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

    @Field(type = FieldType.Keyword)
    private String liveUrl;

    @Field(type = FieldType.Keyword)
    private String token;

    @Field(type = FieldType.Binary)
    private byte[] imgFile;
}
