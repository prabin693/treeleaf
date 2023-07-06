package com.restapi.blog.controller;

import com.restapi.blog.dto.UserDto;
import com.restapi.blog.security.JwtUtil;
import com.restapi.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    //endpoint for registration
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated UserDto userDto, @RequestParam String role) {
        return userService.registerUser(userDto, role);
    }


    //endpoint for login
    @PostMapping("/user/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto loginDTO) {
        try {
            userService.loginUser(loginDTO.getUsername(), loginDTO.getPassword());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }

}


