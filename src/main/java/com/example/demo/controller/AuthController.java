package com.example.demo.controller;

import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user,
                         @RequestParam String role) {
        return userService.registerUser(user, role);
    }

   
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        if (user.getUsername() == null || user.getPassword() == null) {
            throw new UnauthorizedException("Invalid username or password");
        }

        return "Login successful for user: " + user.getUsername();
    }
}
