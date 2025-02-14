package org.example.msasbnotification.repository;

import org.example.msasbnotification.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    // 특정 사용자(userId)의 알림 목록 조회
    List<NotificationEntity> findByReceiverId(Long receiverId);
}
