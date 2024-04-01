package com.lukaj.socialnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/social-network")
public class SecurityController {

    @GetMapping("/login")
    public String login(Model theModel) {

        return "login-page";

    }
}
