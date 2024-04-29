package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.SaveUserStatus;
import com.lukaj.socialnetwork.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    Optional<UserEntity> findById(Integer id);

    UserEntity save(UserEntity user);

    UserEntity getCurrentUser();

    SaveUserStatus registerUser(UserEntity user, String repeatedPassword);

    Integer getLikesSizeByUsername(String username);

    Integer getCommentsSizeByUsername(String username);

    SaveUserStatus modifyUser(UserEntity user, String repeatedPassword);
}