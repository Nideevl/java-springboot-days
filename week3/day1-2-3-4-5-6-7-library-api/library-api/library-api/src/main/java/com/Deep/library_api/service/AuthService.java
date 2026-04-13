package com.Deep.library_api.service;

import com.Deep.library_api.model.User;
import com.Deep.library_api.repository.UserRepository;
import com.Deep.library_api.util.JwtUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String login(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("There is no Username like that."));
        if(passwordEncoder.matches(user.getPassword(), userFromDB.getPassword())){
            return jwtUtil.generateToken(user.getUsername());
        }
        throw new RuntimeException("Invalid password");
    }
}
