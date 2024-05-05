package com.lukaj.socialnetwork.controller;

import com.lukaj.socialnetwork.persistence.entity.SaveUserStatus;
import com.lukaj.socialnetwork.persistence.entity.UserEntity;
import com.lukaj.socialnetwork.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Map;

@Controller
@RequestMapping("/social-network")
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
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
                                      @RequestParam("passwordRepeated") String repeatedPassword,
                                      Model theModel) {

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("user", user);
            return "register-page";
        }

        SaveUserStatus status = userService.registerUser(user, repeatedPassword);

        if (status == SaveUserStatus.NON_UNIQUE_USERNAME) {

            theModel.addAttribute("user", user);
            theModel.addAttribute("nonUniqueUsername", "Username already exists. Try to think of new one.");

            return "register-page";
        }

        if (status == SaveUserStatus.NON_UNIQUE_EMAIL) {

            theModel.addAttribute("user", user);
            theModel.addAttribute("nonUniqueEmail", "Email already exists. Please input different one.");

            return "register-page";
        }

        if (status == SaveUserStatus.PASSWORD_NO_MATCH) {

            theModel.addAttribute("user", user);
            theModel.addAttribute("passwordNoMatch", "Password not repeated correctly. Please try again.");

            return "register-page";
        }

        attributes.addFlashAttribute("registration", "successful");

        return "redirect:/social-network/login";
    }
}
