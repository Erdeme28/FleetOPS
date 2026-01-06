package com.fleet.gateway.controller;

import com.fleet.gateway.entity.User;
import com.fleet.gateway.repository.UserRepository; // Va trebui sa creezi interfata asta!
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // AICI SE INTAMPLA CRIPTAREA - 15 puncte
        String parolaCriptata = passwordEncoder.encode(user.getPassword());
        user.setPassword(parolaCriptata);

        return userRepository.save(user);
    }
}