package org.example.msasbnotification.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower_id", "followee_id"})
})
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 팔로우 하는 유저의 ID
    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    // 팔로우 당하는 유저의 ID
    @Column(name = "followee_id", nullable = false)
    private Long followeeId;
}
