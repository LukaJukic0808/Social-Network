package com.lukaj.socialnetwork.persistence.repository;

import com.lukaj.socialnetwork.persistence.entity.CommentEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findAllByPostOrderByCreatedAtDesc(PostEntity post);
}
