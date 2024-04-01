package com.lukaj.socialnetwork.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/social-network")
public class SecurityController {

    @GetMapping("/login")
    public String login(Model theModel) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/social-network/home"; // Redirect already authenticated users
        }
        return "login-page";
    }

    @GetMapping("/register")
    public String register(Model theModel) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/social-network/home"; // Redirect already authenticated users
        }
        return "register-page";
    }
}
