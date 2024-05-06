package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.LikeEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface LikeService {

    LikeEntity save(LikeEntity likeEntity);

    void remove(LikeEntity likeEntity);

    List<Integer> getLikedPostIDs();

    Set<LikeEntity> getLikesByUser(UserEntity user);

    LikeEntity findOneByUserAndPost(UserEntity user, PostEntity post);
}
