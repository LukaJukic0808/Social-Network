package com.lukaj.socialnetwork.repository;

import com.lukaj.socialnetwork.entity.NotificationEntity;
import com.lukaj.socialnetwork.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    List<NotificationEntity> findAllByReceiverOrderByCreatedAtDesc(UserEntity receiver);
}
