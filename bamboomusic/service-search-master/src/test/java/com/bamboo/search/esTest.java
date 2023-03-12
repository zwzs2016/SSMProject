package com.bamboo.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@SpringBootTest(classes = {esTest.class})
public class esTest {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void createIndex(){

    }
}
