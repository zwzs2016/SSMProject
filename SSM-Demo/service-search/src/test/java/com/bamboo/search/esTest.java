package com.bamboo.search;

import com.bamboo.search.entity.MusicInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import javax.annotation.Resource;

@SpringBootTest(classes = {esTest.class})
public class esTest {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void createIndex(){

    }
}
