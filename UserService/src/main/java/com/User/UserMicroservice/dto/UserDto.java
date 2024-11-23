package com.User.UserMicroservice.dto;

public record UserDto(
        String username,
        String email,
        String password
) {
}
