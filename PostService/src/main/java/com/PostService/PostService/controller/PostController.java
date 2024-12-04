package com.PostService.PostService.controller;

import com.PostService.PostService.dto.PostDto;
import com.PostService.PostService.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogPost")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @PostMapping("/addPost")
    public void addPost(@RequestBody PostDto message) {
        System.out.println("acasss");
        postService.createPost(message);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId) {
        List<PostDto> userPosts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(userPosts);
    }


}
