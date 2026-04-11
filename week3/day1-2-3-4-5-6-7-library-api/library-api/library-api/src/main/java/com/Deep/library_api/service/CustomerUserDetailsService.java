package com.Deep.library_api.service;

import com.Deep.library_api.model.Role;
import com.Deep.library_api.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo ;

    public CustomerUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not present"));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(Role::getPost)
                        .toArray(String[]::new) // it tells you to create an array of type string
                ).build();

    }
}
