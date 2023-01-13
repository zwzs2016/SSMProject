package com.bamboo.search.kafka;

import com.bamboo.search.service.MusicInfoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @Autowired
    MusicInfoService musicInfoService;
    //消费监听
    @KafkaListener(topics = {"wordsendertest"})
    public void onMessageWord(ConsumerRecord<?,?> record){
        System.out.println("简单消费:"+record.topic()+"-"+record.partition()+"-"+record.value());
    }

   @KafkaListener(topics = {"musicinfosearch"})
    public void onMessageMusic(ConsumerRecord<?,?> record){
        System.out.println("简单消费:"+record.topic()+"-"+record.partition()+"-"+record.value());
        if (record.value().equals("add")){
//            musicInfoService.
        }
    }
}
