package com.lukaj.socialnetwork.rest.controller;

import com.lukaj.socialnetwork.persistence.entity.LikeEntity;
import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.rest.request.DeletePostRequest;
import com.lukaj.socialnetwork.rest.request.LikeAndDislikeRequest;
import com.lukaj.socialnetwork.rest.response.LikeAndDislikeResponse;
import com.lukaj.socialnetwork.rest.response.PostResponse;
import com.lukaj.socialnetwork.service.LikeService;
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
@RequestMapping("/social-network/api")
public class PostRESTController {

    private final UserService userService;
    private final PostService postService;
    private final NotificationService notificationService;
    private final LikeService likeService;

    public PostRESTController(UserService userService, PostService postService,
                              NotificationService notificationService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
        this.likeService = likeService;
    }

    @PostMapping(value = "/posts/like", consumes = "application/json")
    @ResponseBody
    public LikeAndDislikeResponse like(@RequestBody LikeAndDislikeRequest request) {

        PostEntity post = postService.findOne(request.postId()).get();
        LikeEntity like = new LikeEntity();
        UserEntity currentUser = userService.getCurrentUser();
        like.setUser(currentUser);
        like.setPost(post);

        likeService.save(like);

        if(!post.getAuthor().getUsername().equals(currentUser.getUsername())) {

            NotificationEntity notification = new NotificationEntity();
            notification.setPost(post);
            notification.setContent("liked your");
            notification.setSender(currentUser);
            notification.setReceiver(post.getAuthor());

            notificationService.save(notification);
        }

        if(post.getAuthor().getUsername().equals(currentUser.getUsername())) {
            return new LikeAndDislikeResponse(true);
        }

        return new LikeAndDislikeResponse(false);
    }

    @PostMapping(value = "/posts/dislike", consumes = "application/json")
    @ResponseBody
    public LikeAndDislikeResponse dislike(@RequestBody LikeAndDislikeRequest request) {

        PostEntity post = postService.findOne(request.postId()).get();
        UserEntity currentUser = userService.getCurrentUser();
        LikeEntity like = likeService.findOneByUserAndPost(currentUser, post);

        likeService.remove(like);

        if(post.getAuthor().getUsername().equals(currentUser.getUsername())) {
            return new LikeAndDislikeResponse(true);
        }

        return new LikeAndDislikeResponse(false);
    }

    @PostMapping(value = "/posts/delete-post", consumes = "application/json")
    @ResponseBody
    public PostResponse deletePost(@RequestBody DeletePostRequest request) {

        PostEntity post = postService.findOne(request.postId()).get();
        UserEntity currentUser = userService.getCurrentUser();

        if(!post.getAuthor().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("Only author of post can delete it.");
        }

        DateTimeFormatter createdAtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ZonedDateTime zonedCreatedAt = post.getCreatedAt().atZone(ZoneId.systemDefault());

        postService.remove(post);

        return new PostResponse(post.getId(), currentUser.getId(),
                post.getImageUrl(), post.getContent(), post.getEnableComments(),
                createdAtFormatter.format(zonedCreatedAt), notificationService.getNotificationsCount(),
                postService.findAllOrderByCreatedAtDescending().size(),
                userService.getLikesSizeByUsername(currentUser.getUsername()),
                userService.getCommentsSizeByUsername(currentUser.getUsername()));
    }
}