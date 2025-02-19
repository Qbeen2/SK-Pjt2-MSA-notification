package org.example.msasbnotification.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 알림 ID

    private Long receiverId;  // 알림을 받을 사용자 ID
    private String type;      // 알림 유형 (dm, follow, post 등)
    private String content;   // 알림 내용

    // 타임스탬프 제거
    // private LocalDateTime timestamp;

    private Boolean isRead;   // 읽음 여부

    @Builder
    public NotificationEntity(Long id, Long receiverId, String type, String content, Boolean isRead) {
        this.id = id;
        this.receiverId = receiverId;
        this.type = type;
        this.content = content;
        this.isRead = isRead;
    }
}
