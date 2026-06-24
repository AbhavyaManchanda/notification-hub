package com.example.notification_hub.service;

import com.example.notification_hub.provider.NotificationSender;
import com.example.notification_hub.provider.NotificationType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class NotificationService {

    private final Map<NotificationType, NotificationSender> senderMap = new EnumMap<>(NotificationType.class);

    // Dependency Injection: Dono implementation beans ko alag-alag mangwaya
    public NotificationService(NotificationSender emailNotificationSender, NotificationSender smsNotificationSender) {
        // Map ke andar keys ab strict ENUMS hain!
        senderMap.put(NotificationType.EMAIL, emailNotificationSender);
        senderMap.put(NotificationType.SMS, smsNotificationSender);
    }

    public void routeNotification(NotificationType type, String recipient, String message) {
        NotificationSender sender = senderMap.get(type);
        if (sender != null) {
            sender.send(recipient, message);
        }
    }
}