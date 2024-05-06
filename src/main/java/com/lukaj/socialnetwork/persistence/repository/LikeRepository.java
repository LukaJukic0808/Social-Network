package com.lukaj.socialnetwork.persistence.repository;

import com.lukaj.socialnetwork.persistence.entity.LikeEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    Set<LikeEntity> findAllByUser(UserEntity user);

    LikeEntity findFirstByUserAndPost(UserEntity user, PostEntity post);
}
