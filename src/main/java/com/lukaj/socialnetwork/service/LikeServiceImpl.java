package com.lukaj.socialnetwork.service;


import com.lukaj.socialnetwork.persistence.entity.LikeEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.persistence.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    public LikeServiceImpl(LikeRepository likeRepository, PostService postService,
                           UserService userService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public LikeEntity save(LikeEntity likeEntity) {
        return likeRepository.save(likeEntity);
    }

    // FetchType.EAGER, same as in CommentServiceImpl
    @Transactional
    @Override
    public void remove(LikeEntity likeEntity) {
        PostEntity post = postService.findOne(likeEntity.getPost().getId()).get();
        post.getLikes().removeIf(like -> Objects.equals(like.getId(), likeEntity.getId()));

        likeRepository.delete(likeEntity);
    }

    @Override
    public List<Integer> getLikedPostIDs() {

        List<Integer> postIds = new ArrayList<>();
        Set<LikeEntity> likes = getLikesByUser(userService.getCurrentUser());

        for(var like: likes) {
            postIds.add(like.getPost().getId());
        }

        return postIds;
    }

    @Override
    public Set<LikeEntity> getLikesByUser(UserEntity user) {
        return likeRepository.findAllByUser(user);
    }

    @Override
    public LikeEntity findOneByUserAndPost(UserEntity user, PostEntity post) {
        return likeRepository.findFirstByUserAndPost(user, post);
    }
}
