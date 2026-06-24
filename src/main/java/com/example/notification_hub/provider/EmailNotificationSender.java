package com.example.notification_hub.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//@Component("emailSender") // Tells Spring IoC to manage this class with this specific name
@Component
@Primary
public class EmailNotificationSender implements NotificationSender {

    private final JavaMailSender mailSender;

    public EmailNotificationSender(JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }
    @Async
    @Override
    public void send(String recipient, String message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(recipient);
            mail.setSubject("Alert from Notification Hub");
            mail.setText(message);
            mail.setFrom("abhavyaa19@gmail.com");

            mailSender.send(mail); // This triggers the actual network call!
            System.out.println("🚀 Real email fired off to " + recipient);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }

}