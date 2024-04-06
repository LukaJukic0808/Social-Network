package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.entity.UserEntity;
import com.lukaj.socialnetwork.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Map;

@Controller
@RequestMapping("/social-network")
public class SecurityController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityController(UserService userService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/login")
    public String login(Model theModel, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/social-network/home"; // Redirect already authenticated users
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            theModel.addAttribute("registration", inputFlashMap.get("registration"));
        }
        return "login-page";
    }

    @GetMapping("/register")
    public String register(Model theModel) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/social-network/home"; // Redirect already authenticated users
        }

        theModel.addAttribute("user", new UserEntity());

        return "register-page";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") UserEntity user,
                                      BindingResult bindingResult, RedirectAttributes attributes,
                                      Model theModel) {

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("user", user);
            return "register-page";
        }

        if (userService.findByUsername(user.getUsername()) != null) {

            theModel.addAttribute("user", user);
            theModel.addAttribute("nonUniqueUsername", "Username already exists in database.");

            return "register-page";
        }

        if (userService.findByEmail(user.getEmail()) != null) {

            theModel.addAttribute("user", user);
            theModel.addAttribute("nonUniqueEmail", "Email already exists in database.");

            return "register-page";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userService.save(user);

        attributes.addFlashAttribute("registration", "successful");

        return "redirect:/social-network/login";
    }
}
