package com.PostService.PostService.controller;

import com.PostService.PostService.dto.PostDto;
import com.PostService.PostService.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogPost")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/addPost")
    public void addPost(@RequestBody PostDto message) {
        System.out.println("acasss");
        postService.createPost(message);
    }

    // **Obtener todos los posts**
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // **Obtener un post por ID**
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String postId) {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // **Eliminar un post por ID**
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // **Obtener todos los posts de un usuario por su ID**
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId) {
        List<PostDto> userPosts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(userPosts);
    }


}
