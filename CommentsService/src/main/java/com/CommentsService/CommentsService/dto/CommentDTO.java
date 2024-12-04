package com.CommentsService.CommentsService.dto;

public record CommentDTO(
    Long ownerId,
    Long commenterId,
    Long postId,
    String content
) {}
