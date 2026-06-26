package com.company.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }
    
    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }
}