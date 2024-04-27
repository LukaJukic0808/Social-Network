package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.PostEntity;
import com.lukaj.socialnetwork.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public PostEntity save(PostEntity post) {
        post.setAuthor(userService.getCurrentUser());
        return postRepository.save(post);
    }
}
