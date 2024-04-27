package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.entity.PostEntity;
import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.service.PostService;
import com.lukaj.socialnetwork.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/social-network")
public class HomeController {

    private final UserService userService;
    private final PostService postService;

    public HomeController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/home")
    public String home(Model theModel, HttpServletRequest request) {

        List<Integer> postIds = new ArrayList<>();
        UserEntity user = userService.getCurrentUser();
        List<PostEntity> posts = userService.getPostsByUsername(user.getUsername());

        for (PostEntity post : posts) {
            postIds.add(post.getId());
        }

        theModel.addAttribute("user", userService.getCurrentUser());
        theModel.addAttribute("usersPost", postIds.get(1));

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            theModel.addAttribute("postCreated", inputFlashMap.get("postCreated"));
        }

        return "home";
    }

    @GetMapping("/new-post")
    public String createPost(Model theModel) {

        theModel.addAttribute("post", new PostEntity());
        theModel.addAttribute("user", userService.getCurrentUser());

        return "new-post-page";
    }

    @PostMapping("/new-post")
    public String processPostCreation(@Valid @ModelAttribute("post") PostEntity post,
                                      BindingResult bindingResult, RedirectAttributes attributes,
                                      Model theModel) {

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("post", post);
            theModel.addAttribute("user", userService.getCurrentUser());
            return "new-post-page";
        }

        postService.save(post);

        attributes.addFlashAttribute("postCreated", "successful");

        return "redirect:/social-network/home";
    }
}
