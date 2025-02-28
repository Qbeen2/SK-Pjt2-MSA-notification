package org.example.msasbnotification.repository;

import org.example.msasbnotification.entity.PostEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // Containing을 사용하면 "% %" 효과를 얻게됨
    List<PostEntity> findByContentContaining (String findString);

    // gid 검색
    List<PostEntity> findByGid(Long gid);

    List<PostEntity> findByUid(Long uid);

    List<PostEntity> findByPid(Long pid);


    // uid로 게시글 삭제
    @Modifying
    @Transactional
    void deleteByUid(Long uid);
}
