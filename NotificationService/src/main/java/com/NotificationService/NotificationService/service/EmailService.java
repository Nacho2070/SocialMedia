package com.NotificationService.NotificationService.service;

import com.NotificationService.NotificationService.Dto.MailRequest;
import com.NotificationService.NotificationService.Dto.MailResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private Configuration config;

    public MailResponse sendEmail(MailRequest request) {
        MailResponse response = new MailResponse();
        MimeMessage message = sender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        model.put("name", request.getName());

        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
            Template t = template(request.getBody());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            sender.send(message);
            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);
        } catch (IOException | TemplateException | MessagingException e) {
            response.setMessage("Mail Sending failure : "+e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }

    private Template template(String templateUri) throws IOException {
        if (templateUri == null || !templateUri.endsWith(".ftl")) {
            throw new IllegalArgumentException("Template Uri cannot be null or empty");
        }

      return config.getTemplate(templateUri);
    }
}
