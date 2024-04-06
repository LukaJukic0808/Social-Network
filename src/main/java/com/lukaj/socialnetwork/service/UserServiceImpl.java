package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public UserEntity findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity user) {

        return userRepository.save(user);
    }
}