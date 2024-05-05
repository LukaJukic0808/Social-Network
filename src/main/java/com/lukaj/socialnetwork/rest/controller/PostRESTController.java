package com.lukaj.socialnetwork.rest.controller;

import com.lukaj.socialnetwork.service.LikeService;
import com.lukaj.socialnetwork.service.NotificationService;
import com.lukaj.socialnetwork.service.PostService;
import com.lukaj.socialnetwork.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social-network")
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
}
