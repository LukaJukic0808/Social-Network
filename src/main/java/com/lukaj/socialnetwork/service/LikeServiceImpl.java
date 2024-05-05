package com.lukaj.socialnetwork.service;


import com.lukaj.socialnetwork.persistence.entity.LikeEntity;
import com.lukaj.socialnetwork.persistence.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    @Override
    public LikeEntity like(LikeEntity like) {
        return likeRepository.save(like);
    }

    @Transactional
    @Override
    public void dislike(LikeEntity like) {
        likeRepository.delete(like);
    }
}
