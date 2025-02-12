package org.example.msasbnotification.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void processDmNotification(String message) {
        // DM 이벤트 처리 로직 구현 (예: JSON 파싱 후 대상 사용자에게 DM 알림 생성)
        System.out.println("Processing DM notification: " + message);
    }

    public void processFollowNotification(String message) {
        // 팔로우 이벤트 처리 로직 구현 (예: JSON 파싱 후 대상 사용자에게 '새로운 팔로워' 알림 생성)
        System.out.println("Processing Follow notification: " + message);
    }

    public void processNewPostNotification(String message) {
        // 신규 포스트 이벤트 처리 로직 구현 (예: JSON 파싱 후 작성자의 팔로워들에게 '신규 포스트' 알림 생성)
        System.out.println("Processing New Post notification: " + message);
    }
}
