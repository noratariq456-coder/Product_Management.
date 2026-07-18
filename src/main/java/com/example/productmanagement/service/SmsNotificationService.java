package com.example.productmanagement.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("smsNotificationService")
public class SmsNotificationService implements NotificationService {

    // Lifecycle method - called after bean construction
    @PostConstruct
    public void init() {
        System.out.println("SmsNotificationService initialized and ready to send SMS.");
    }

    // Lifecycle method - called before bean is destroyed
    @PreDestroy
    public void destroy() {
        System.out.println("SmsNotificationService is shutting down.");
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("[SMS NOTIFICATION] " + message);
    }
}
