package org.example.msasbnotification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msasbnotification.dto.NotificationCreateDto;
import org.example.msasbnotification.dto.NotificationDto;
import org.example.msasbnotification.service.NotificationService;
import org.example.msasbnotification.repository.NotificationRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    // 알림 생성 엔드포인트
    @PostMapping(consumes = "application/json", produces = "application/json")
    public NotificationDto createNotification(
            @RequestBody NotificationCreateDto createDto,
            @RequestHeader("X-Auth-User") String email,
            @RequestHeader("Authorization") String accessToken) {

        log.info("Create notification request from email: {} with token: {}", email, accessToken);

        NotificationDto notificationDto = NotificationDto.builder()
                .receiverId(createDto.getUserId())
                .type(createDto.getType())
                .content(createDto.getMessage())
                .timestamp(LocalDateTime.now())
                .isRead(createDto.getIsRead()) // 전달된 값, 보통 false
                .build();

        return notificationService.saveNotification(notificationDto);
    }

    // 특정 사용자의 알림 조회
    @GetMapping(value = "/{userId}", produces = "application/json")
    public List<NotificationDto> getUserNotifications(
            @PathVariable Long userId,
            @RequestHeader("X-Auth-User") String email,
            @RequestHeader("Authorization") String accessToken) {

        log.info("Received get notifications request for user: {} from email: {} with token: {}", userId, email, accessToken);
        return notificationService.convertToDtoList(notificationRepository.findByReceiverId(userId));
    }

    // 알림 읽음 처리
    @PostMapping(value = "/{id}/read", produces = "application/json")
    public void markAsRead(
            @PathVariable Long id,
            @RequestHeader("X-Auth-User") String email,
            @RequestHeader("Authorization") String accessToken) {

        log.info("Marking notification {} as read for user: {} with token: {}", id, email, accessToken);
        notificationService.markNotificationAsRead(id);
    }
}
