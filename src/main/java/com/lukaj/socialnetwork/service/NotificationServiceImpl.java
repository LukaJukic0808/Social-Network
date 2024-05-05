package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.persistence.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public NotificationEntity save(NotificationEntity notificationEntity) {
        return notificationRepository.save(notificationEntity);
    }

    @Transactional
    @Override
    public void remove(NotificationEntity notificationEntity) {
        notificationRepository.delete(notificationEntity);
    }
}
