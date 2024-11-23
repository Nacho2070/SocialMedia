package com.NotificationService.NotificationService.listener;

import com.NotificationService.NotificationService.Dto.MailRequest;
import com.NotificationService.NotificationService.event.User;
import com.NotificationService.NotificationService.service.EmailService;
import com.NotificationService.NotificationService.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class UserEventListener {

    private final EmailService emailService;

    @KafkaListener(topics= "userRegistered")
    public void handlerOrderNotifications(ConsumerRecord<String,String> message){
        log.info("User added{}",message.value());
       User userDto = JsonUtils.fromJson(message.value(), User.class);
        System.out.println(userDto);
        MailRequest mailRequest = new MailRequest();
        mailRequest.setFrom("arizanacho2077@gmail.com");
        mailRequest.setTo(userDto.email());
        mailRequest.setSubject("Registration made successfully"+userDto.username());
        mailRequest.setName(userDto.username());

        emailService.sendEmail(mailRequest);
    }
}
