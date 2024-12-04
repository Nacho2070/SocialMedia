package com.NotificationService.NotificationService.listener;

import com.NotificationService.NotificationService.Dto.MailRequest;
import com.NotificationService.NotificationService.service.EmailService;
import com.NotificationService.NotificationService.utils.Template;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@AllArgsConstructor
@Component
@Slf4j
public class CommentListener {
    private final EmailService emailService;

    @KafkaListener(topics = "commentPost")
    public void commenterId(String payload){
       String[] parts = payload.split(" ");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid payload format: " + payload);
        }

        MailRequest request = new MailRequest();
        request.setName(parts[0]);
        request.setTo(parts[1]);
        request.setFrom("arizanacho2077@gmail.com");
        request.setSubject(parts[0]+ " commented on your post!");
        request.setBody(Template.COMMENT_TEMPLATE);
        emailService.sendEmail(request);

    }
}
