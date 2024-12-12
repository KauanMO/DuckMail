package com.duckmail.infra.email;

import com.duckmail.dtos.email.QueuedEmailDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {
    private final String EMAIL_FROM = "DuckMail";
    private final JavaMailSender javaMailSender;
    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender, JavaMailSender javaMailSender) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
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
            System.out.println(e.getMessage());
        }
    }
}