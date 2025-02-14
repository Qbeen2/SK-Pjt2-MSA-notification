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
        NotificationEntity entity = NotificationEntity.builder()
                .receiverId(dto.getReceiverId())
                .type(dto.getType())
                .content(dto.getContent())
                .timestamp(dto.getTimestamp())
                .build();

        NotificationEntity savedEntity = notificationRepository.saveNotification(entity);
        return convertToDto(savedEntity);
    }

    // 특정 사용자의 알림 조회
    public List<NotificationDto> getNotificationsByUser(Long userId) {
        List<NotificationEntity> entities = notificationRepository.findByReceiverId(userId);
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Entity -> DTO 변환
    private NotificationDto convertToDto(NotificationEntity entity) {
        return NotificationDto.fromEntity(entity);
    }

    // Entity 리스트 -> DTO 리스트 변환 (새로운 메서드 추가)
    public List<NotificationDto> convertToDtoList(List<NotificationEntity> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }


}
