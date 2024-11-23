package com.PostService.PostService.utils;

public record UserDto(
        String id,
        String username,
        String email,
        String password
) {}