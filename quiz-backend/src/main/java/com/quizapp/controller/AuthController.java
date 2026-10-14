
package com.quizapp.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizapp.entity.User;
import com.quizapp.repository.UserRepository;
import com.quizapp.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    public AuthController(
            UserRepository userRepository,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (request.name() == null || request.name().isBlank()
                || request.email() == null || request.email().isBlank()
                || request.password() == null
                || request.password().length() < 8) {
            return ResponseEntity.badRequest().body(
                    Map.of("message",
                            "Name and email are required; password must be at least 8 characters"));
        }

        String name = request.name().trim();
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("message",
                            "An account with this email already exists"));
        }

        User user = new User(
                name,
                email,
                passwordEncoder.encode(request.password())
        );

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "id", savedUser.getId(),
                        "name", savedUser.getName(),
                        "email", savedUser.getEmail(),
                        "message", "Registration successful"
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request.email() == null || request.email().isBlank()
                || request.password() == null
                || request.password().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Email and password are required"));
        }

        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null
                || !passwordEncoder.matches(
                        request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("message", "Invalid email or password"));
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "message", "Login successful"
                )
        );
    }

    public record RegisterRequest(
            String name,
            String email,
            String password
    ) {}

    public record LoginRequest(
            String email,
            String password
    ) {}
}
