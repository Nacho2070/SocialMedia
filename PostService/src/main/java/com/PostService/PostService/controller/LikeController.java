package com.PostService.PostService.controller;

import com.PostService.PostService.persistence.entity.BlogPost;
import com.PostService.PostService.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/getAll/{postId}")
    public int getAllLikes(@PathVariable Long postId){
        return likeService.getAllLikesOnPost(postId);
    }

    @GetMapping("/isLiked/{userId}/{postId}")
    public ResponseEntity<Boolean> likePost(@PathVariable Long userId, @PathVariable Long postId) {
        return new ResponseEntity<>(likeService.isLiked(userId, postId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @PostMapping("/add/{userId}/{postId}")
    public BlogPost addLike(@PathVariable Long userId, @PathVariable Long postId) {
        return likeService.likePost(userId,postId);
    }

    @GetMapping("/delete/{userId}/{postId}")
    public void deleteLike(@PathVariable Long userId, @PathVariable Long postId) {
        likeService.deleteLike(userId,postId);
    }

    @GetMapping("/delete/{postId}")
    public void deleteLikes(@PathVariable Long postId) {
        likeService.deleteAllLikes(postId);
    }
}
