package com.lukaj.socialnetwork.service;

import com.lukaj.socialnetwork.persistence.entity.CommentEntity;
import com.lukaj.socialnetwork.persistence.entity.LikeEntity;
import com.lukaj.socialnetwork.persistence.entity.SaveUserStatus;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.persistence.repository.UserRepository;
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
    public SaveUserStatus registerUser(UserEntity user, String repeatedPassword) {

        if (findByUsername(user.getUsername()) != null) {
            return SaveUserStatus.NON_UNIQUE_USERNAME;
        }

        if (findByEmail(user.getEmail()) != null) {
            return SaveUserStatus.NON_UNIQUE_EMAIL;
        }

        if (!user.getPassword().equals(repeatedPassword)) {
            return SaveUserStatus.PASSWORD_NO_MATCH;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        save(user);

        return SaveUserStatus.SUCCESSFUL;
    }

    // lazy loading
    @Override
    public Integer getLikesSizeByUsername(String username) {

        // entity is now managed by Hibernate session (fetched from DB with repository)
        UserEntity user = findByUsername(username);
        Set<LikeEntity> likes = user.getLikes();
        if(likes.isEmpty()) {   // trigger collection to be saved (isEmpty)
            return 0;
        }
        return likes.size();
    }

    // lazy loading
    @Override
    public Integer getCommentsSizeByUsername(String username) {

        // entity is now managed by Hibernate session (fetched from DB with repository)
        UserEntity user = findByUsername(username);
        Set<CommentEntity> comments = user.getComments();
        if(comments.isEmpty()) {    // trigger collection to be saved (isEmpty)
            return 0;
        }
        return comments.size();
    }

    @Override
    public SaveUserStatus modifyUser(UserEntity user, String repeatedPassword) {

        if (!user.getPassword().equals(repeatedPassword)) {
            return SaveUserStatus.PASSWORD_NO_MATCH;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        save(user);

        return SaveUserStatus.SUCCESSFUL;
    }
}