package org.example.msasbnotification.dto;

import lombok.Builder;
import lombok.Data;
import org.example.msasbnotification.entity.NotificationEntity;

@Data
@Builder
public class NotificationDto {
    private Long id;
    private Long receiverId;
    private String type;
    private String content;
    // 타임스탬프 제거
    // private LocalDateTime timestamp;
    private Boolean isRead;

    public static NotificationDto fromEntity(NotificationEntity entity) {
        return NotificationDto.builder()
                .id(entity.getId())
                .receiverId(entity.getReceiverId())
                .type(entity.getType())
                .content(entity.getContent())
                .isRead(entity.getIsRead())
                .build();
    }
}
