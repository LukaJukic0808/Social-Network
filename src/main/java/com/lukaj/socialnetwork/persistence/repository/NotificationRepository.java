package com.lukaj.socialnetwork.persistence.repository;

import com.lukaj.socialnetwork.persistence.entity.NotificationEntity;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    List<NotificationEntity> findAllByReceiverOrderByCreatedAtDesc(UserEntity receiver);
}
