package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<NotificationEntity> findAllNotificationsByReceiver(UserEntity receiver);

    NotificationEntity save(NotificationEntity notificationEntity);

    void remove(NotificationEntity notificationEntity);

    Optional<NotificationEntity> findById(Integer id);

    Integer getNotificationsCount();
}
