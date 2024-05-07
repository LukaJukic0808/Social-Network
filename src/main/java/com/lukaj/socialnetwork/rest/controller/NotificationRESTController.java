package com.lukaj.socialnetwork.rest.controller;

import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.rest.request.DeleteNotificationRequest;
import com.lukaj.socialnetwork.rest.response.NotificationResponse;
import com.lukaj.socialnetwork.service.NotificationService;
import com.lukaj.socialnetwork.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social-network/api")
public class NotificationRESTController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationRESTController(NotificationService notificationService,
                                      UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @DeleteMapping(value = "/notifications/delete-notification", consumes = "application/json")
    @ResponseBody
    public NotificationResponse deleteNotification(@RequestBody DeleteNotificationRequest request) {

        NotificationEntity notificationToDelete = notificationService.findById(request.id()).get();

        UserEntity currentUser = userService.getCurrentUser();

        if(!notificationToDelete.getReceiver().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("Only receiver of notification can delete it.");
        }

        notificationService.remove(notificationToDelete);

        return new NotificationResponse(notificationService.getNotificationsCount());
    }
}
