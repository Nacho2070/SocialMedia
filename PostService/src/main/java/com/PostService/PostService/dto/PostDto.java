package com.PostService.PostService.dto;

public record PostDto (
        String title,
        String authorId,
        String content
){}
