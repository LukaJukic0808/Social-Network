package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.entity.PostEntity;
import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.service.PostService;
import com.lukaj.socialnetwork.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/social-network")
public class ProfileController {

    private final UserService userService;
    private final PostService postService;

    public ProfileController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable Integer userId,
                                  Model theModel) {

        Optional<UserEntity> userOpt = userService.findById(userId);

        if(userOpt.isPresent()) {
            UserEntity userProfile = userOpt.get();
            UserEntity currentUser = userService.getCurrentUser();
            List<PostEntity> posts = postService.findAllByAuthorOrderByCreatedAtDescending(userProfile);
            theModel.addAttribute("userProfile", userProfile);
            theModel.addAttribute("user", currentUser);
            theModel.addAttribute("posts", posts);
            theModel.addAttribute("totalLikes", userService.getLikesSizeByUsername(userProfile.getUsername()));
            theModel.addAttribute("totalComments", userService.getCommentsSizeByUsername(userProfile.getUsername()));
            return "user-profile-page";
        }

        return "redirect:/social-network/home";
    }
}
