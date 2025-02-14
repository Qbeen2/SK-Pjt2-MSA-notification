package org.example.msasbnotification.repository;

import org.example.msasbnotification.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    // 특정 사용자(userId)의 알림 목록 조회
    List<NotificationEntity> findByReceiverId(Long receiverId);

    // 알림 저장을 위한 기본 메서드 (JpaRepository의 save 사용)
    default NotificationEntity saveNotification(NotificationEntity notification) {
        return save(notification);
    }
}
