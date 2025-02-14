package org.example.msasbnotification.kafka;

import org.example.msasbnotification.dto.NotificationDto;
import org.example.msasbnotification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "notification-dm", groupId = "notification-group")
    public void consumeDM(NotificationDto notificationDto) {
        log.info("📩 DM 알림 수신: {}", notificationDto);
        notificationService.saveNotification(notificationDto);
    }

    @KafkaListener(topics = "notification-follow", groupId = "notification-group")
    public void consumeFollow(NotificationDto notificationDto) {
        log.info("➕ 팔로우 알림 수신: {}", notificationDto);
        notificationService.saveNotification(notificationDto);
    }

    @KafkaListener(topics = "notification-post-create", groupId = "notification-group")
    public void consumeNewPost(NotificationDto notificationDto) {
        log.info("📝 새 게시글 알림 수신: {}", notificationDto);
        notificationService.saveNotification(notificationDto);
    }

    @KafkaListener(topics = "notification-comment", groupId = "notification-group")
    public void consumeNewComment(NotificationDto notificationDto) {
        log.info("💬 댓글 알림 수신: {}", notificationDto);
        notificationService.saveNotification(notificationDto);
    }

    // 좋아요 알림도 추가할것

}
