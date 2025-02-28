package org.example.msasbnotification.repository;


import org.example.msasbnotification.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    @Query("SELECT f.followerId FROM FollowEntity f WHERE f.followeeId = :followeeId")
    List<Long> findFollowerIdsByFolloweeId(@Param("followeeId") Long followeeId);

}