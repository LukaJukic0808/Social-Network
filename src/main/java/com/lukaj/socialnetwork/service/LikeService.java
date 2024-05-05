package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.LikeEntity;

public interface LikeService {

    LikeEntity like(LikeEntity like);

    void dislike(LikeEntity like);
}
