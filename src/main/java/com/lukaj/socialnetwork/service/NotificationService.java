package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.NotificationEntity;
import com.lukaj.socialnetwork.entity.UserEntity;

import java.util.List;

public interface NotificationService {

    List<NotificationEntity> findAllNotificationsByReceiver(UserEntity receiver);

    NotificationEntity save(NotificationEntity notificationEntity);
}
