package com.bamboo.search.kafka;

import com.bamboo.search.constant.request.ElasticsearchExecuteStatus;
import com.bamboo.search.service.MusicInfoService;
import com.google.gson.Gson;
import com.uwan.common.constant.KafkaSendMessageOperate;
import com.uwan.common.dto.KafkaSendMessageDTO;
import com.uwan.common.dto.MusicInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        Gson gson=new Gson();
        KafkaSendMessageDTO kafkaSendMessageDTO=gson.fromJson(String.valueOf(record.value()),KafkaSendMessageDTO.class);
        if (kafkaSendMessageDTO.getOperate()== KafkaSendMessageOperate.ADD_MUSICINFO.getValue()){
            MusicInfoDTO musicInfoDTO = gson.fromJson(kafkaSendMessageDTO.getMessage(), MusicInfoDTO.class);
            int status = musicInfoService.addMusicInfo(musicInfoDTO);
            if (status== ElasticsearchExecuteStatus.INSERT_SUCCESS.getValue()){
                log.debug("简单消费:"+record.topic()+"-"+record.partition()+"-"+record.value()+":"+ElasticsearchExecuteStatus.INSERT_SUCCESS.getMsg());
            }else {
                log.debug("简单消费:"+record.topic()+"-"+record.partition()+"-"+record.value()+":"+ElasticsearchExecuteStatus.INSERT_FAIL.getMsg());
            }
        }else if (kafkaSendMessageDTO.getOperate()==KafkaSendMessageOperate.DELETE_MUSICINFO.getValue()){
            String author= kafkaSendMessageDTO.getMessage();
            int result=musicInfoService.delete(author);
            if (result==ElasticsearchExecuteStatus.DELETE_SUCCESS.getValue()){
                log.debug("简单消费:"+record.topic()+"-"+record.partition()+"-"+record.value()+":"+ElasticsearchExecuteStatus.DELETE_SUCCESS.getMsg());
            }else {
                log.debug("简单消费:"+record.topic()+"-"+record.partition()+"-"+record.value()+":"+ElasticsearchExecuteStatus.DELETE_FAIL.getMsg());
            }
        }
    }
}
