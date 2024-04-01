package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping("/social-network")
public class TestController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/home")
    public String sayHello(Model theModel) {

        UserEntity user = new UserEntity();
        user.setUsername("lovroj");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setFirstName("Lovro");
        user.setLastName("Jukic");
        user.setEmail("lovro@gmail.com");
        user.setCreatedAt(Instant.now());
        if(userRepository.findByUsername("lovroj") == null)
            userRepository.save(user);

        return "helloworld";
    }
}
