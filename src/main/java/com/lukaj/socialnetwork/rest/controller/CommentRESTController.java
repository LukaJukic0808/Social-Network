package com.lukaj.socialnetwork.rest.controller;

import com.lukaj.socialnetwork.persistence.entity.CommentEntity;
import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.rest.request.CreateCommentRequest;
import com.lukaj.socialnetwork.rest.request.DeleteCommentRequest;
import com.lukaj.socialnetwork.rest.response.CommentResponse;
import com.lukaj.socialnetwork.service.CommentService;
import com.lukaj.socialnetwork.service.NotificationService;
import com.lukaj.socialnetwork.service.PostService;
import com.lukaj.socialnetwork.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/social-network")
public class CommentRESTController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    private final NotificationService notificationService;

    public CommentRESTController(CommentService commentService, UserService userService,
                                 PostService postService, NotificationService notificationService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
    }

    @PostMapping(value = "/comments/add-comment", consumes = "application/json")
    @ResponseBody
    public CommentResponse createComment(@RequestBody CreateCommentRequest request) {

        UserEntity currentUser = userService.getCurrentUser();
        PostEntity post = postService.findOne(request.postId()).get();
        if(!post.getEnableComments()) {
            throw new RuntimeException("Commenting on this post is disabled.");
        }
        CommentEntity comment = new CommentEntity();
        comment.setAuthor(currentUser);
        comment.setPost(post);
        comment.setContent(request.commentContent());

        CommentEntity savedComment = commentService.save(comment);

        if(!post.getAuthor().getUsername().equals(currentUser.getUsername())) {

            NotificationEntity notification = new NotificationEntity();
            notification.setPost(post);
            notification.setContent("commented on your");
            notification.setSender(currentUser);
            notification.setReceiver(post.getAuthor());

            notificationService.save(notification);
        }

        DateTimeFormatter createdAtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ZonedDateTime zonedCreatedAt = savedComment.getCreatedAt().atZone(ZoneId.systemDefault());

        return new CommentResponse(savedComment.getId(), currentUser.getId(),
                currentUser.getUsername(), createdAtFormatter.format(zonedCreatedAt),
                savedComment.getContent());
    }

    @PostMapping(value = "/comments/delete-comment", consumes = "application/json")
    @ResponseBody
    public CommentResponse deleteComment(@RequestBody DeleteCommentRequest request) {

        CommentEntity comment = commentService.findById(request.id()).get();
        UserEntity currentUser = userService.getCurrentUser();

        if(!comment.getAuthor().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("Only author of comment can delete it.");
        }

        DateTimeFormatter createdAtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ZonedDateTime zonedCreatedAt = comment.getCreatedAt().atZone(ZoneId.systemDefault());

        CommentResponse response = new CommentResponse(comment.getId(), currentUser.getId(),
                currentUser.getUsername(), createdAtFormatter.format(zonedCreatedAt), comment.getContent());

        commentService.remove(comment);

        return response;
    }

}
