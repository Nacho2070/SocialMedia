package com.NotificationService.NotificationService.listener;

import com.NotificationService.NotificationService.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostEventListener {

    @KafkaListener(topics= "PostTopic")
    public void handlerOrderNotifications(ConsumerRecord<String,String> message){
        log.info("PostAdded{}",message.value());

    }
}
