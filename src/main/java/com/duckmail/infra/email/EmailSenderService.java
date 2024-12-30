package com.duckmail.infra.email;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.enums.RecipientStatus;
import com.duckmail.services.DeliveryErrorLogService;
import com.duckmail.services.RecipientService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class EmailSenderService {
    private static final String emailFrom = "DuckMail";
    private final JavaMailSender javaMailSender;
    private final DeliveryErrorLogService deliveryErrorLogService;
    private final RecipientService recipientService;

    public EmailSenderService(JavaMailSender javaMailSender, DeliveryErrorLogService deliveryErrorLogService, RecipientService recipientService) {
        this.javaMailSender = javaMailSender;
        this.deliveryErrorLogService = deliveryErrorLogService;
        this.recipientService = recipientService;
    }

    public void sendQueuedEmail(QueuedEmailDTO emailDTO) {
        sendEmail(emailDTO.to(), emailDTO.subject(), emailDTO.body(), emailDTO.recipientId());
    }

    protected void sendEmail(String to, String subject, String body, Long recipientId) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(mimeMessage);
            recipientService.changeRecipientStatus(recipientId, RecipientStatus.SENT);
        } catch (Exception e) {
            deliveryErrorLogService.registerError(e.getMessage(), LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime(), recipientId);
            recipientService.changeRecipientStatus(recipientId, RecipientStatus.FAILED);
        }
    }
}