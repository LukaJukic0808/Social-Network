package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.entity.NotificationEntity;
import com.lukaj.socialnetwork.entity.PostEntity;
import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.service.NotificationService;
import com.lukaj.socialnetwork.service.PostService;
import com.lukaj.socialnetwork.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/social-network")
public class HomeController {

    private final UserService userService;
    private final PostService postService;
    private final NotificationService notificationService;

    public HomeController(UserService userService, PostService postService,
                          NotificationService notificationService) {
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
    }

    @GetMapping("/home")
    public String home(Model theModel, HttpServletRequest request) {

        UserEntity currentUser = userService.getCurrentUser();

        List<PostEntity> postsDescending = postService.findAllOrderByCreatedAtDescending();
        List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);

        theModel.addAttribute("user", currentUser);
        theModel.addAttribute("posts", postsDescending);
        theModel.addAttribute("notificationsSize", usersNotifications.size());

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            theModel.addAttribute("postCreated", inputFlashMap.get("postCreated"));
            theModel.addAttribute("postEdited", inputFlashMap.get("postEdited"));
        }

        return "home";
    }

    @GetMapping("/notifications")
    public String getNotifications(Model theModel) {

        UserEntity currentUser = userService.getCurrentUser();
        List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);
        theModel.addAttribute("notifications", usersNotifications);
        theModel.addAttribute("notificationsSize", usersNotifications.size());
        theModel.addAttribute("user", currentUser);

        return "notifications-page";
    }

}
