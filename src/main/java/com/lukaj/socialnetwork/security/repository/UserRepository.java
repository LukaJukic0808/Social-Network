package com.lukaj.socialnetwork.security.repository;

import com.lukaj.socialnetwork.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);
}
