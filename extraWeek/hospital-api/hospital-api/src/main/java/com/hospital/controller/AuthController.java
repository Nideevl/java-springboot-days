package com.hospital.controller;

import com.hospital.dto.request.LoginRequest;
import com.hospital.dto.request.PatientRegisterRequest;
import com.hospital.dto.response.AuthResponse;
import com.hospital.entity.User;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.service.AuthService;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Login attempt for email : {}", request.getEmail());

        User user = userService.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException(
                "User not found with email: " + request.getEmail()
        ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Invalid password of the user : {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, null, null, "Invalid password"));
        }

        if (!user.getIsEnabled()) {
            log.warn("User account has been disabled : {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new AuthResponse(null, null, null, null, "Account is disabled"));
        }

        String token = authService.generateToken(user);
        log.info("Login successful for email : {}", request.getEmail());

        return ResponseEntity.ok(new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRoles().stream().map(User.UserRole::name).toList(),
                "Logged In successfully"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody PatientRegisterRequest request) {
        if(userService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(null, null, null, null, "Email already registered"));
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(User.UserRole.ROLE_PATIENT));
        user.setIsEnabled(true);

        User savedUser = userService.c
    }

}
