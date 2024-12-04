package com.PostService.PostService.service;

import com.PostService.PostService.persistence.entity.BlogPost;
import com.PostService.PostService.persistence.entity.BlogPostRepository;
import com.PostService.PostService.persistence.entity.Like;
import com.PostService.PostService.utils.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@Service
@Log4j2
public class LikeService {

    private final PostService postService;
    private final BlogPostRepository blogPostRepository;

    public int getAllLikesOnPost(Long postId) {
       BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
       List<Like> likeList = blogPost.getLikes();
       return likeList.size();
    }

    public Boolean isLiked(Long userId, Long postId) {
        BlogPost blogPost = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return blogPost.getLikes().stream()
                .anyMatch(like -> like.getUserLikeToId().equals(userId));
    }

    public BlogPost likePost(Long userId, Long postId) {
        BlogPost blogPost = postService.getPostId(postId);
        Mono<UserDto> user = postService.fetchUserById(userId);

        if(blogPost != null && user != null) {
            Like like = Like.builder()
                    .userLikeToId(userId)
                    .build();
            blogPost.getLikes().add(like);

           return blogPostRepository.save(blogPost);
        }
        throw new RuntimeException("User or BlogPost not found");
   }

    public void deleteLike(Long userId, Long postId) {
        BlogPost blogPost = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        blogPost.getLikes().removeIf(like -> like.getUserLikeToId().equals(userId));
        blogPostRepository.save(blogPost);
    }

    public void deleteAllLikes(Long postId) {
        BlogPost blogPost = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        blogPost.getLikes().clear();
        blogPostRepository.save(blogPost);
    }
}
