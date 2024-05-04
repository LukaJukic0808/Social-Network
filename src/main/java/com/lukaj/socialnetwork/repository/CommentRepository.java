package com.lukaj.socialnetwork.repository;

import com.lukaj.socialnetwork.entity.CommentEntity;
import com.lukaj.socialnetwork.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findAllByPostOrderByCreatedAtDesc(PostEntity post);
}
