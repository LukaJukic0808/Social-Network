package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.entity.CommentEntity;
import com.lukaj.socialnetwork.entity.LikeEntity;
import com.lukaj.socialnetwork.entity.RegisterUserStatus;
import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserEntity findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity save(UserEntity user) {

        return userRepository.save(user);
    }

    @Override
    public UserEntity getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return findByUsername(username);
    }

    @Override
    public RegisterUserStatus registerUser(UserEntity user, String repeatedPassword) {

        if (findByUsername(user.getUsername()) != null) {
            return RegisterUserStatus.NON_UNIQUE_USERNAME;
        }

        if (findByEmail(user.getEmail()) != null) {
            return RegisterUserStatus.NON_UNIQUE_EMAIL;
        }

        if (!user.getPassword().equals(repeatedPassword)) {
            return RegisterUserStatus.PASSWORD_NO_MATCH;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        save(user);

        return RegisterUserStatus.SUCCESSFUL;
    }

    @Override
    public Integer getLikesSizeByUsername(String username) {
        UserEntity user = findByUsername(username);
        Set<LikeEntity> likes = user.getLikes();
        if(likes.isEmpty()) {
            return 0;
        }
        return likes.size();
    }

    @Override
    public Integer getCommentsSizeByUsername(String username) {
        UserEntity user = findByUsername(username);
        Set<CommentEntity> comments = user.getComments();
        if(comments.isEmpty()) {
            return 0;
        }
        return comments.size();
    }

}