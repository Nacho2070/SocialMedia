package com.CommentsService.CommentsService.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    Long countByPostId(Long postId);

    void deleteByPostId(Long postId);
}
