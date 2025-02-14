package org.example.msasbnotification.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nid;

    private Long receiverUid;  // 알림을 받을 사용자
    private String type;       // 알림 타입 (DM, FOLLOW, POST 등)
    private Long relatedPid;   // 관련된 게시글 ID (선택적)
    private Long relatedUid;   // 관련된 사용자 ID (선택적)
    private String message;    // 알림 메시지
    private LocalDateTime createdAt;
    private boolean status;    // 읽음 여부 (true: 읽음, false: 안 읽음)
}
