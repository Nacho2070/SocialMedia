package com.User.UserMicroservice.dto;

public record UserIdDto(
        String id,
        String username,
        String email,
        String password
) {
}
