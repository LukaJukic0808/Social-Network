package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.PostEntity;
import com.lukaj.socialnetwork.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostEntity save(PostEntity post);

    List<PostEntity> findAllOrderByCreatedAtDescending();

    Optional<PostEntity> findOne(Integer id);

    List<PostEntity> findAllByAuthorOrderByCreatedAtDescending(UserEntity author);
}
