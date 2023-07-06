package com.restapi.blog.service;

import com.restapi.blog.dto.UserDto;
import com.restapi.blog.model.Role;
import com.restapi.blog.model.User;
import com.restapi.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<?> registerUser(UserDto userDto, String role) {
        User user = userDto.getUser();
        user.setRole(Role.valueOf(role.toUpperCase()));
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPassword().equals(password)) {
            return "Login successful";
        }
        throw new RuntimeException("Invalid credentials");
    }

    public ResponseEntity<?> registerAdmin(UserDto userDto) {
        User user = userDto.getUser();
        user.setRole(Role.ADMIN);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok("Admin registered successfully");
    }
}
