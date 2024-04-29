package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.NotificationEntity;
import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationEntity> findAllNotificationsByReceiver(UserEntity receiver) {
        return notificationRepository.findAllByReceiverOrderByCreatedAtDesc(receiver);
    }
}
