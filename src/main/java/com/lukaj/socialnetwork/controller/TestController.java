package com.lukaj.socialnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/social-network")
public class TestController {

    @GetMapping("/home")
    public String sayHello(Model theModel) {

        return "helloworld";

    }
}
