package com.lukaj.socialnetwork.repository;

import com.lukaj.socialnetwork.entity.PostEntity;
import com.lukaj.socialnetwork.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    List<PostEntity> findAllByOrderByCreatedAtDesc();
    List<PostEntity> findAllByAuthorOrderByCreatedAtDesc(UserEntity author);

}
