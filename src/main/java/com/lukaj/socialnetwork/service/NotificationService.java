package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;

import java.util.List;

public interface NotificationService {

    List<NotificationEntity> findAllNotificationsByReceiver(UserEntity receiver);

    NotificationEntity save(NotificationEntity notificationEntity);

    void remove(NotificationEntity notificationEntity);
}
