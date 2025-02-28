package org.example.msasbnotification.service;

import lombok.RequiredArgsConstructor;
import org.example.msasbnotification.dto.NotificationDto;
import org.example.msasbnotification.entity.NotificationEntity;
import org.example.msasbnotification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    // 알림 저장
    public NotificationDto saveNotification(NotificationDto dto) {
        System.out.println("Service : Saving notification");
        NotificationEntity entity = NotificationEntity.builder()
                .receiverId(dto.getReceiverId())
                .type(dto.getType())
                .content(dto.getContent())
                .isRead(false) // 기본적으로 읽지 않은 상태
                .build();

        NotificationEntity savedEntity = notificationRepository.save(entity);
        return convertToDto(savedEntity);
    }

    // 특정 사용자의 알림 조회
    public List<NotificationDto> getNotificationsByUser(Long userId) {
        List<NotificationEntity> entities = notificationRepository.findByReceiverId(userId);
        return convertToDtoList(entities);
    }

    // 알림을 읽음 상태로 업데이트
    public void markNotificationAsRead(Long id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    // Entity -> DTO 변환
    public NotificationDto convertToDto(NotificationEntity entity) {
        return NotificationDto.fromEntity(entity);
    }

    // Entity 리스트 -> DTO 리스트 변환
    public List<NotificationDto> convertToDtoList(List<NotificationEntity> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // 모든 알림 조회 (읽음 처리 없이)
    public List<NotificationDto> getAllNotifications() {
        List<NotificationEntity> entities = notificationRepository.findAll();
        return convertToDtoList(entities);
    }
}