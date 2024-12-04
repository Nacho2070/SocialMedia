package com.CommentsService.CommentsService.dto;

public record UserDto(
        String id,
        String username,
        String email,
        String password
) {
}
