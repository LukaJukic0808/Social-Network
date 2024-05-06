package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.persistence.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
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

    @Override
    public Optional<NotificationEntity> findById(Integer id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Integer getNotificationsCount() {
        return findAllNotificationsByReceiver(userService.getCurrentUser()).size();
    }
}
