package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.RegisterUserStatus;
import com.lukaj.socialnetwork.entity.UserEntity;

public interface UserService {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity save(UserEntity user);

    UserEntity getCurrentUser();

    RegisterUserStatus registerUser(UserEntity user, String repeatedPassword);
}