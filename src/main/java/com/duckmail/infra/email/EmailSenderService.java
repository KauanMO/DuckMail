package com.duckmail.infra.email;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.services.DeliveryErrorLogService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        sendEmail(emailDTO.to(), emailDTO.subject(), emailDTO.body(), emailDTO.recipientId());
    }

    @Async
    protected void sendEmail(String to, String subject, String body, Long recipientId) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            helper.setFrom(EMAIL_FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            Path path = Paths.get("src/main/java/com/duckmail/assets/logo.png");
            Resource resource = new FileSystemResource(path.toFile());
            helper.addInline("logo", resource);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            deliveryErrorLogService.registerError(e.getMessage(), LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime(), recipientId);
        }
    }
}