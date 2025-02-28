package org.example.msasbnotification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEventDto {
    private Long pid;    // 게시글 ID
    private Long uid;    // 게시글 작성자 ID
    private String type;
    private Long gid;
    private String content; // 게시글 내용 (요약 등)
}

