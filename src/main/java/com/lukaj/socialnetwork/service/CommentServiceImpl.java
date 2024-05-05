package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.CommentEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentServiceImpl(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    @Override
    public List<CommentEntity> findAllByPost(PostEntity post) {
        return commentRepository.findAllByPostOrderByCreatedAtDesc(post);
    }

    @Transactional
    @Override
    public CommentEntity save(CommentEntity commentEntity) {
        return commentRepository.save(commentEntity);
    }

    @Transactional
    @Override
    public void remove(CommentEntity commentEntity) {

        // because of FetchType.EAGER inside postEntity, these 2 steps are necessary, to sync the database
        // it is also necessary to get post with findById (or any other method from repo)
        // this is for it to become managed entity inside Hibernate session (automatic sync with DB)
        PostEntity post = postService.findOne(commentEntity.getPost().getId()).get();
        post.getComments().removeIf(comment -> Objects.equals(comment.getId(), commentEntity.getId()));

        commentRepository.delete(commentEntity);
    }

    @Override
    public Optional<CommentEntity> findById(Integer id) {
        return commentRepository.findById(id);
    }
}