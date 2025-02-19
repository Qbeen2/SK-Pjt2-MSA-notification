package org.example.msasbnotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMRequest {
    private String senderId;
    private String receiverId;
    private String content;
}
