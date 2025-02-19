package org.example.msasbnotification.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msasbnotification.dto.NotificationDto;
import org.example.msasbnotification.dto.DMRequest;
import org.example.msasbnotification.dto.FollowRequest;
import org.example.msasbnotification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "dm", groupId = "notification-group")
    public void consumeDM(String message) {
        try {
            // JSON 메시지를 DMRequest 객체로 변환
            DMRequest dmRequest = objectMapper.readValue(message, DMRequest.class);
            // NotificationDto 생성: content는 "senderId: message"
            NotificationDto notificationDto = NotificationDto.builder()
                    .receiverId(Long.parseLong(dmRequest.getReceiverId()))
                    .type("DM")
                    .content(dmRequest.getSenderId() + ": " + dmRequest.getContent())
                    .isRead(false)
                    .build();
            log.info("📩 DM 알림 수신: {}", notificationDto);
            notificationService.saveNotification(notificationDto);
        } catch (Exception e) {
            log.error("❌ DM 메시지 파싱 실패: {}", message, e);
        }
    }

    @KafkaListener(topics = "follow", groupId = "notification-group")
    public void consumeFollow(String message) {
        try {
            // JSON 메시지를 FollowRequest 객체로 변환
            FollowRequest followRequest = objectMapper.readValue(message, FollowRequest.class);
            // NotificationDto 생성: content는 "followerId followed/unfollowed you"
            NotificationDto notificationDto = NotificationDto.builder()
                    .receiverId(Long.parseLong(followRequest.getFolloweeId()))
                    .type(followRequest.getAction().equalsIgnoreCase("follow") ? "Follow" : "Unfollow")
                    .content(followRequest.getFollowerId() + (followRequest.getAction().equalsIgnoreCase("follow") ? " followed you" : " unfollowed you"))
                    .isRead(false)
                    .build();
            log.info("➕ Follow 알림 수신: {}", notificationDto);
            notificationService.saveNotification(notificationDto);
        } catch (Exception e) {
            log.error("❌ Follow 메시지 파싱 실패: {}", message, e);
        }
    }
    @KafkaListener(topics = "post", groupId = "notification-group")
    public void consumePostEvent(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long uid = jsonNode.get("uid").asLong();
            Long pid = jsonNode.get("pid").asLong();
            JsonNode followersNode = jsonNode.get("followers");

            List<Long> followers = new ArrayList<>();
            if (followersNode.isArray()) {
                for (JsonNode node : followersNode) {
                    followers.add(node.asLong());
                }
            }

            for (Long followerId : followers) {
                NotificationDto notification = NotificationDto.builder()
                        .receiverId(followerId)
                        .content("팔로우한 사용자가 새로운 게시글을 올렸습니다! (게시글 ID: " + pid + ")")
                        .build();

                notificationService.saveNotification(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "like", groupId = "notification-group")
    public void consumeLikeEvent(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long pid = jsonNode.get("pid").asLong();
            Long uid = jsonNode.get("uid").asLong();
            Long authorId = jsonNode.get("authorId").asLong();

            NotificationDto notification = NotificationDto.builder()
                    .receiverId(authorId)
                    .content("사용자 " + uid + "님이 게시글(ID: " + pid + ")을 좋아합니다.")
                    .build();

            notificationService.saveNotification(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "comment", groupId = "notification-group")
    public void consumeCommentEvent(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long pid = jsonNode.get("pid").asLong();
            Long uid = jsonNode.get("uid").asLong();
            Long authorId = jsonNode.get("authorId").asLong();
            String content = jsonNode.get("content").asText();

            NotificationDto notification = NotificationDto.builder()
                    .receiverId(authorId)
                    .content("사용자 " + uid + "님이 게시글(ID: " + pid + ")에 댓글을 남겼습니다: " + content)
                    .build();

            notificationService.saveNotification(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
