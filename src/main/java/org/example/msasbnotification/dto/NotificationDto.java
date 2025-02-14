package org.example.msasbnotification.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDto {
    private Long id;           // 알림 ID
    private Long receiverId;   // 알림을 받는 사용자 ID
    private String type;       // 알림 유형 (FOLLOW, DM, POST_CREATE 등)
    private String message;    // 알림 메시지
    private LocalDateTime createdAt; // 생성 날짜
}
