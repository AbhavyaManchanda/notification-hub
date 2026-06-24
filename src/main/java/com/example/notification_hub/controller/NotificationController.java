package com.example.notification_hub.controller;


import com.example.notification_hub.provider.NotificationType;
import com.example.notification_hub.service.NotificationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //to make it available for the web requests

@RequestMapping("/api/notify") //base address   "localhost:8080/api/notify"
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService=notificationService;
    }

    @GetMapping
    public String triggerNotification(
            @RequestParam NotificationType type, // String ki jagah ENUM data type!
            @RequestParam @NotBlank @Email(message = "Please enter the right Email Address!") String to,
            @RequestParam @NotBlank @Size(min = 5, message = "Message should be minimum 5 characters") String message) {

        notificationService.routeNotification(type, to, message);
        return "Notification sent successfully via " + type + "!";
    }

}
