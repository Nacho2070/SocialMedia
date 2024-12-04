package com.PostService.PostService.persistence.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findAllByAuthorId(String userId);

    Long countByLike(Integer postId);
}
