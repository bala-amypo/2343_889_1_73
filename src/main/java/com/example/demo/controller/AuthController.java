package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserService userService,
            JwtTokenProvider tokenProvider,
            PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest req) {

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());

        return userService.registerUser(user, req.getRole());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {

        User user = userService.findByUsername(req.getUsernameOrEmail());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = tokenProvider.generateToken(user);

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRoles()
                        .stream()
                        .map(r -> r.getName())
                        .collect(Collectors.toList())
        );
    }
}
