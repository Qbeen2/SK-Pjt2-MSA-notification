package org.example.msasbnotification.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msasbnotification.dto.NotificationDto;
import org.example.msasbnotification.dto.DMRequest;
import org.example.msasbnotification.dto.FollowRequest;
import org.example.msasbnotification.dto.PostEventDto;
import org.example.msasbnotification.entity.PostEntity;
import org.example.msasbnotification.repository.FollowRepository;
import org.example.msasbnotification.repository.PostRepository;
import org.example.msasbnotification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
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

    private final PostRepository postRepository;

    @KafkaListener(topics = "like", groupId = "notification-group")
    public void consumeLikeEvent(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long pid = jsonNode.get("pid").asLong();
            Long likerId = jsonNode.get("uid").asLong();
            // authorId가 JSON에 없으므로, pid로 PostEntity를 조회해서 얻어옴
            Optional<PostEntity> postOpt = postRepository.findById(pid);
            if (!postOpt.isPresent()) {
                log.warn("좋아요 이벤트 무시: 게시글 {}가 DB에 존재하지 않음", pid);
                return;
            }
            Long authorId = postOpt.get().getUid();
            NotificationDto notificationDto = NotificationDto.builder()
                    .receiverId(authorId)
                    .type("Like")
                    .content("사용자 " + likerId + "님이 게시글(ID: " + pid + ")을 좋아합니다.")
                    //.timestamp(LocalDateTime.now())
                    .isRead(false)
                    .build();
            log.info("좋아요 알림 저장 완료: {}", notificationDto);
            notificationService.saveNotification(notificationDto);
        } catch (Exception e) {
            log.error("❌ 좋아요 메시지 파싱 실패: {}", message, e);
        }
    }

    @KafkaListener(topics = "comment", groupId = "notification-group")
    public void consumeCommentEvent(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long pid = jsonNode.get("pid").asLong();
            Long commenterId = jsonNode.get("uid").asLong();
            String commentContent = jsonNode.get("content").asText();
            // authorId가 JSON에 없으므로, pid로 PostEntity를 조회하여 얻어옴
            Optional<PostEntity> postOpt = postRepository.findById(pid);
            if (!postOpt.isPresent()) {
                log.warn("댓글 이벤트 무시: 게시글 {}가 DB에 존재하지 않음", pid);
                return;
            }
            Long authorId = postOpt.get().getUid();
            NotificationDto notificationDto = NotificationDto.builder()
                    .receiverId(authorId)
                    .type("Comment")
                    .content("사용자 " + commenterId + "님이 게시글(ID: " + pid + ")에 댓글을 남겼습니다: " + commentContent)
                    //.timestamp(LocalDateTime.now())
                    .isRead(false)
                    .build();
            log.info("댓글 알림 저장 완료: {}", notificationDto);
            notificationService.saveNotification(notificationDto);
        } catch (Exception e) {
            log.error("❌ 댓글 메시지 파싱 실패: {}", message, e);
        }
    }

    private final FollowRepository followRepository;

    @KafkaListener(topics = "post", groupId = "notification-group")
    public void consumePostEvent(String message) {
        try {
            System.out.println("Consume json msg : " + message);
            // JSON 메시지를 PostEventDto로 변환
            PostEventDto event = objectMapper.readValue(message, PostEventDto.class);
//                if ( event.getType().equals("group")){
//                    System.out.println("Consume group post");
//                    Long authorId = event.getUid();
//                    Long postId = event.getPid();
//                    String type = event.getType();
//                    Long gid = event.getGid();
//                    String content = event.getContent();
//
//
//                    List<Long> followerIds = followRepository.findFollowerIdsByFolloweeId(authorId); // gid
//                    for (Long followerId : followerIds) {
//                        NotificationDto notification = NotificationDto.builder()
//                                .receiverId(followerId)
//                                .type("POST")
//                                .content("사용자 " + authorId + "가 새로운 게시글을 올렸습니다. (게시글 ID: " + postId + ")")
//                                //.timestamp(LocalDateTime.now())
//                                .isRead(false)
//                                .build();
//                        notificationService.saveNotification(notification);
//                        log.info("알림 저장 완료: {}", notification);
//                    }
//                }
            if ( event.getType().equals("public") ) {
                System.out.println("Consume public post");
                Long authorId = event.getUid();
                Long postId = event.getPid();

                List<Long> followerIds = followRepository.findFollowerIdsByFolloweeId(authorId);
                System.out.println("Follower Ids : " + followerIds);
                for (Long followerId : followerIds) {
                    System.out.println("Build Notification");
                    NotificationDto notification = NotificationDto.builder()
                            .receiverId(followerId)
                            .type("POST")
                            .content("사용자 " + authorId + "가 새로운 게시글을 올렸습니다. (게시글 ID: " + postId + ")")
                            //.timestamp(LocalDateTime.now())
                            .isRead(false)
                            .build();
                    System.out.println("Build complete");
                    notificationService.saveNotification(notification);
                    log.info("알림 저장 완료: {}", notification);
                }
            }
        } catch (Exception e) {
            log.error("Error processing post event", e);
        }
    }
}
