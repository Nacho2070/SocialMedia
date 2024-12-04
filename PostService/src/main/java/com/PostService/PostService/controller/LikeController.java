package com.PostService.PostService.controller;

import com.PostService.PostService.persistence.entity.BlogPost;
import com.PostService.PostService.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/getAll/{postId}")
    public int getAllLikes(@PathVariable Long postId){
        return likeService.getAllLikesOnPost(postId);
    }

    @GetMapping("/isLiked/{userId}/{postId}")
    public ResponseEntity<Boolean> likePost(@PathVariable Long userId, @PathVariable Long postId) {
        return new ResponseEntity<>(likeService.isLiked(userId, postId), HttpStatus.OK);
    }

    @GetMapping("/add/{userId}/{postId}")
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
