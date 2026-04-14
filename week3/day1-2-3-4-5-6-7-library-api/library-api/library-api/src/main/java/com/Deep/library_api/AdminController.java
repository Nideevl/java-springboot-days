package com.Deep.library_api;

import com.Deep.library_api.exception.RoleNotFoundException;
import com.Deep.library_api.model.Role;
import com.Deep.library_api.model.User;
import com.Deep.library_api.model.UserDTO;
import com.Deep.library_api.repository.RoleRepository;
import com.Deep.library_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public AdminController(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Transactional
    @PostMapping("/admin/assign-role")
    public User setNewAdmin(@RequestBody UserDTO userDTO) {
        User user = userRepo.findByUsername(userDTO.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found."));
        Role role = roleRepo.findByPost(userDTO.getRole()).orElseThrow(()-> new RoleNotFoundException("No such role exist."));

        List<Role> roles = new ArrayList<>(user.getRoles());
        roles.add(role);
        user.setRoles(roles);

        return userRepo.save(user);
    }
}
