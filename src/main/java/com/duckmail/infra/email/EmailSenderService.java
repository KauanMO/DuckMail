package com.duckmail.infra.email;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.services.DeliveryErrorLogService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class EmailSenderService {
    private final String EMAIL_FROM = "DuckMail";
    private final JavaMailSender javaMailSender;
    private final JavaMailSender mailSender;
    private final DeliveryErrorLogService deliveryErrorLogService;

    public EmailSenderService(JavaMailSender mailSender, JavaMailSender javaMailSender, DeliveryErrorLogService deliveryErrorLogService) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.deliveryErrorLogService = deliveryErrorLogService;
    }

    public void sendQueuedEmail(QueuedEmailDTO emailDTO) {
        sendEmail(emailDTO.to(), emailDTO.subject(), emailDTO.body());
    }

    @Async
    protected void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(EMAIL_FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            deliveryErrorLogService.registerError(e.getMessage(), LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
        }
    }
}