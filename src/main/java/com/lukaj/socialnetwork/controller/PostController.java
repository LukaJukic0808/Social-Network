package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.persistence.entity.CommentEntity;
import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.service.CommentService;
import com.lukaj.socialnetwork.service.NotificationService;
import com.lukaj.socialnetwork.service.PostService;
import com.lukaj.socialnetwork.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/social-network")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final NotificationService notificationService;
    private final CommentService commentService;

    public PostController(UserService userService, PostService postService,
                          NotificationService notificationService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postId}")
    public String getPost(Model theModel, @PathVariable Integer postId,
                            HttpServletRequest request) {

        Optional<PostEntity> post = postService.findOne(postId);
        UserEntity currentUser = userService.getCurrentUser();
        List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);

        if (post.isEmpty()) {
            return "redirect:/social-network/home";
        }

        PostEntity currentPost = post.get();

        List<CommentEntity> comments = commentService.findAllByPost(currentPost);

        theModel.addAttribute("post", currentPost);
        theModel.addAttribute("user", currentUser);
        theModel.addAttribute("comments", comments);
        theModel.addAttribute("notificationsSize", usersNotifications.size());

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            theModel.addAttribute("postCreated", inputFlashMap.get("postCreated"));
            theModel.addAttribute("postEdited", inputFlashMap.get("postEdited"));
        }

        return "post-detail-page";
    }

    @GetMapping("/edit-post/{postId}")
    public String editPost(Model theModel, @PathVariable Integer postId) {

        Optional<PostEntity> post = postService.findOne(postId);
        UserEntity currentUser = userService.getCurrentUser();
        List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);


        if (post.isEmpty() || !post.get().getAuthor().getUsername().equals(currentUser.getUsername())) {
            return "redirect:/social-network/home";
        }

        theModel.addAttribute("post", post.get());
        theModel.addAttribute("user", currentUser);
        theModel.addAttribute("notificationsSize", usersNotifications.size());


        return "edit-post-page";
    }

    @PostMapping("/edit-post")
    public String processEditPost(@Valid @ModelAttribute("post") PostEntity post,
                                  BindingResult bindingResult, RedirectAttributes attributes,
                                  Model theModel) {

        UserEntity currentUser = userService.getCurrentUser();

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("post", post);
            theModel.addAttribute("user", currentUser);
            return "edit-post-page";
        }

        PostEntity postInDB = postService.findOne(post.getId()).get();

        if (postInDB.getEnableComments() != post.getEnableComments() ||
                !postInDB.getContent().equals(post.getContent()) ||
                !postInDB.getImageUrl().equals(post.getImageUrl())) {

            post.setCreatedAt(Instant.now());
            postService.save(post);
            attributes.addFlashAttribute("postEdited", "successful");
        }

        return String.format("redirect:/social-network/posts/%d", post.getId());
    }

    @GetMapping("/new-post")
    public String createPost(Model theModel) {

        UserEntity currentUser = userService.getCurrentUser();
        List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);

        theModel.addAttribute("post", new PostEntity());
        theModel.addAttribute("user", currentUser);
        theModel.addAttribute("notificationsSize", usersNotifications.size());


        return "new-post-page";
    }

    @PostMapping("/new-post")
    public String processPostCreation(@Valid @ModelAttribute("post") PostEntity post,
                                      BindingResult bindingResult, RedirectAttributes attributes,
                                      Model theModel) {

        UserEntity currentUser = userService.getCurrentUser();

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("post", post);
            theModel.addAttribute("user", currentUser);
            return "new-post-page";
        }

        PostEntity savedPost = postService.save(post);

        attributes.addFlashAttribute("postCreated", "successful");

        return String.format("redirect:/social-network/posts/%d", savedPost.getId());
    }

}
