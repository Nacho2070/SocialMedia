package com.NotificationService.NotificationService.Dto;

public record Comment (
        String commentId,
        Long ownerId,
        Long commenterId,
        Long postId,
        String content
){}