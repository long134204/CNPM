package com.example.loginregister.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

     JavaMailSender mailSender;
    private static final String REFRESH_PASSWORD_HTML_MAIL_TEMPLATE_PATH = "/templates/event-refresh-password-template.html";

    private static final String MANAGER_EMAIL = "trailheadsalesforce7@gmail.com";


    public void sendEmail(String to, String token) throws MessagingException, IOException {
        String subject = "REFRESH PASSWORD";
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(MANAGER_EMAIL);
        message.setRecipients(MimeMessage.RecipientType.TO,to);
        message.setSubject(subject);

        String template =prepareSendEmail(token);
        String htmlContent = template.replace("${Token}", token);
        message.setContent(htmlContent, "text/html; charset=utf-8");
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//        String htmlContent = prepareSendEmail(token);
//
//        helper.setFrom(MANAGER_EMAIL);
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlContent, true);



        mailSender.send(message);
//        mailSender.send(message);

    }

    private String prepareSendEmail(String token) throws IOException {
        Path path = Paths.get("src/main/resources/templates/event-refresh-password-template.html");
        return Files.readString(path, StandardCharsets.UTF_8);
//        String template = loadHtmlTemplate(REFRESH_PASSWORD_HTML_MAIL_TEMPLATE_PATH);
//        Date date = new Date();
//        return String.format(template,
//                date,
//                token
//        );
    }



    private String loadHtmlTemplate(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Cannot find template file: " + path);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }
}
