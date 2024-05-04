package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.CommentEntity;
import com.lukaj.socialnetwork.entity.PostEntity;

import java.util.List;

public interface CommentService {

    List<CommentEntity> findAllByPost(PostEntity post);

    CommentEntity save(CommentEntity commentEntity);
}
