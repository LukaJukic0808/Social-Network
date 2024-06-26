package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.PostEntity;
import com.lukaj.socialnetwork.persistence.entity.SaveUserStatus;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.service.LikeService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/social-network")
public class ProfileController {

    private final UserService userService;
    private final PostService postService;
    private final NotificationService notificationService;
    private final LikeService likeService;

    public ProfileController(UserService userService, PostService postService,
                             NotificationService notificationService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
        this.likeService = likeService;
    }

    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable Integer userId,
                                  Model theModel, HttpServletRequest request) {

        Optional<UserEntity> userOpt = userService.findById(userId);

        if(userOpt.isPresent()) {
            UserEntity userProfile = userOpt.get();
            UserEntity currentUser = userService.getCurrentUser();
            List<PostEntity> posts = postService.findAllByAuthorOrderByCreatedAtDescending(userProfile);
            List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);
            List<Integer> likedPostIDs = likeService.getLikedPostIDs();
            theModel.addAttribute("userProfile", userProfile);
            theModel.addAttribute("user", currentUser);
            theModel.addAttribute("posts", posts);
            theModel.addAttribute("likedPostIDs", likedPostIDs);
            theModel.addAttribute("notificationsSize", usersNotifications.size());
            theModel.addAttribute("totalLikes", userService.getLikesSizeByUsername(userProfile.getUsername()));
            theModel.addAttribute("totalComments", userService.getCommentsSizeByUsername(userProfile.getUsername()));
            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if(inputFlashMap != null) {
                theModel.addAttribute("userModified", inputFlashMap.get("management"));
                theModel.addAttribute("postDeleted", inputFlashMap.get("postDeleted"));
            }
            return "user-profile-page";
        }

        return "redirect:/social-network/home";
    }

    @GetMapping("/manage-user")
    public String showManageUserPage(Model theModel) {

        UserEntity currentUser = userService.getCurrentUser();

        List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);

        theModel.addAttribute("user", currentUser);
        theModel.addAttribute("notificationsSize", usersNotifications.size());

        return "manage-user-page";
    }

    @PostMapping("/manage-user")
    public String processUserManagement(@Valid @ModelAttribute("user") UserEntity user,
                                        BindingResult bindingResult, RedirectAttributes attributes,
                                        @RequestParam("passwordRepeated") String repeatedPassword,
                                        Model theModel) {

        UserEntity currentUser = userService.getCurrentUser();
        List<NotificationEntity> usersNotifications = notificationService.findAllNotificationsByReceiver(currentUser);

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("user", user);
            theModel.addAttribute("notificationsSize", usersNotifications.size());
            return "manage-user-page";
        }

        SaveUserStatus status = userService.modifyUser(user, repeatedPassword);

        if (status == SaveUserStatus.PASSWORD_NO_MATCH) {

            theModel.addAttribute("user", user);
            theModel.addAttribute("passwordNoMatch", "Password not repeated correctly. Please try again.");
            theModel.addAttribute("notificationsSize", usersNotifications.size());

            return "manage-user-page";
        }

        attributes.addFlashAttribute("management", "successful");

        return String.format("redirect:/social-network/users/%d", user.getId());
    }
}
