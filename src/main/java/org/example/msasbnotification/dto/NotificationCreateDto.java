package org.example.msasbnotification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationCreateDto {
    @JsonProperty("userId")
    private Long userId;  // 외부 요청에서는 userId로 받음 (내부에서는 receiverId)

    @JsonProperty("message")
    private String message;  // 외부 요청에서는 message로 받음 (내부에서는 content)

    @JsonProperty("type")
    private String type;     // 알림 유형

    @JsonProperty("isRead")
    private Boolean isRead;  // 요청 값이 있지만, 보통 생성 시 false로 설정
}
