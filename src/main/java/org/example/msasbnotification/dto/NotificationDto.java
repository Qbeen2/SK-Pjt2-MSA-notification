package org.example.msasbnotification.dto;

import lombok.Builder;
import lombok.Data;
import org.example.msasbnotification.entity.NotificationEntity;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private Long receiverId;  // 알림을 받을 사용자 ID
    private String type;      // 알림 유형 (dm, follow, post.create 등)
    private String content;   // 알림 내용
    private LocalDateTime timestamp;

    @Builder
    public NotificationDto(Long id, Long receiverId, String type, String content, LocalDateTime timestamp) {
        this.id = id;
        this.receiverId = receiverId;
        this.type = type;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Entity -> DTO 변환 메서드
    public static NotificationDto fromEntity(NotificationEntity entity) {
        return NotificationDto.builder()
                .id(entity.getId())
                .receiverId(entity.getReceiverId())
                .type(entity.getType())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
