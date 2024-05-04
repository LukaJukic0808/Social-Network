package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.CommentEntity;
import com.lukaj.socialnetwork.entity.PostEntity;
import com.lukaj.socialnetwork.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentEntity> findAllByPost(PostEntity post) {
        return commentRepository.findAllByPostOrderByCreatedAtDesc(post);
    }

    @Override
    public CommentEntity save(CommentEntity commentEntity) {
        return commentRepository.save(commentEntity);
    }
}