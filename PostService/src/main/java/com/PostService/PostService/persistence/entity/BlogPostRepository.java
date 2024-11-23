package com.PostService.PostService.persistence.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BlogPostRepository extends MongoRepository<BlogPost, String> {
    List<BlogPost> findAllByAuthorId(String userId);
}
