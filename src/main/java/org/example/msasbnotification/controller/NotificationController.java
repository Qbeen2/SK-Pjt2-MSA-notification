package org.example.msasbnotification.controller;

import org.example.msasbnotification.dto.NotificationDto;
import org.example.msasbnotification.repository.NotificationRepository;
import org.example.msasbnotification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationService notificationService, NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

    // 특정 사용자(userId)의 알림 조회
    @GetMapping("/{userId}")
    public List<NotificationDto> getUserNotifications(@PathVariable Long userId) {
        return notificationService.convertToDtoList(notificationRepository.findByReceiverId(userId));
    }
}
