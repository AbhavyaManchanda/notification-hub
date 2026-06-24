package com.example.notification_hub.provider;

import org.springframework.stereotype.Component;


@Component("smsSender")
public class SmsNotificationSender implements NotificationSender {

    @Override
    public void send(String recipient,String message){
        System.out.println("💬 SMS sent to " + recipient + ": " + message);
    }
}
