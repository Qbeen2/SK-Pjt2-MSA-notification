package org.example.msasbnotification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;             // 알림 ID

    private Long receiverId;      // 알림을 받을 사용자 ID
    private String type;          // 알림 유형 (dm, follow, post.create 등)
    private String content;       // 알림 내용
    private LocalDateTime timestamp;  // 알림 생성 시간

    @Builder
    public NotificationEntity(Long id, Long receiverId, String type, String content, LocalDateTime timestamp) {
        this.id = id;
        this.receiverId = receiverId;
        this.type = type;
        this.content = content;
        this.timestamp = timestamp;
    }
}
