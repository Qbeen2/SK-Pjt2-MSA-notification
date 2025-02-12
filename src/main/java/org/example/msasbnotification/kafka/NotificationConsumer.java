package org.example.msasbnotification.kafka;

import org.example.msasbnotification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "dm.events", groupId = "notification-group")
    public void consumeDmEvent(String message) {
        System.out.println("Received DM event: " + message);
        notificationService.processDmNotification(message);
    }

    @KafkaListener(topics = "follow.events", groupId = "notification-group")
    public void consumeFollowEvent(String message) {
        System.out.println("Received Follow event: " + message);
        notificationService.processFollowNotification(message);
    }

    @KafkaListener(topics = "post.created", groupId = "notification-group")
    public void consumeNewPostEvent(String message) {
        System.out.println("Received New Post event: " + message);
        notificationService.processNewPostNotification(message);
    }
}
