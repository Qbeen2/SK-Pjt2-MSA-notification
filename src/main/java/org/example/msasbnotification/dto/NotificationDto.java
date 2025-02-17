package org.example.msasbnotification.dto;

import lombok.Builder;
import lombok.Data;
import org.example.msasbnotification.entity.NotificationEntity;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDto {
    private Long id;
    private Long receiverId;
    private String type;
    private String content;
    private LocalDateTime timestamp;
    private Boolean isRead;   // 읽음 여부

    public static NotificationDto fromEntity(NotificationEntity entity) {
        return NotificationDto.builder()
                .id(entity.getId())
                .receiverId(entity.getReceiverId())
                .type(entity.getType())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp())
                .isRead(entity.getIsRead())
                .build();
    }
}
