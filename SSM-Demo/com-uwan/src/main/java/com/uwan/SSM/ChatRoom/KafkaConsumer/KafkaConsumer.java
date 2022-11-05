package com.uwan.SSM.ChatRoom.KafkaConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    //消费监听
//    @KafkaListener(topics = {"wordsendertest"})
//    public void onMessage1(ConsumerRecord<?,?> record){
//        System.out.println("简单消费:"+record.topic()+"-"+record.partition()+"-"+record.value());
//    }
}
