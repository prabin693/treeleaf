package com.restapi.blog.dto;

import com.restapi.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Username cannot be null")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter and one number")
    private String password;

    public User getUser() {
        return User.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }
}
