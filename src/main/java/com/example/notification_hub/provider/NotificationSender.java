package com.example.notification_hub.provider;

public interface NotificationSender {
    void send(String recipient,String  message);
}
