package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.CommentEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentEntity> findAllByPost(PostEntity post);

    CommentEntity save(CommentEntity commentEntity);

    void remove(CommentEntity commentEntity);

    Optional<CommentEntity> findById(Integer id);
}
