package com.CommentsService.CommentsService.controller;

import com.CommentsService.CommentsService.dto.CommentDTO;
import com.CommentsService.CommentsService.persistence.Comment;
import com.CommentsService.CommentsService.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @PostMapping("/add")
    public void addComment(@RequestBody CommentDTO commentRequest) {
        commentService.addComment(commentRequest);
    }

    // get the number of comments for displaying them in the post
    @GetMapping("/count/{postId}")
    public Long getNumberOfComments(@PathVariable Long postId) {

        return commentService.countCommentsByPostId(postId);
    }

    // get the comments for displaying them in the post's comment section
    @GetMapping("/comments/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    // delete a comment by id
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    // delete all comments within a post
    @DeleteMapping("/deleteAll/{postId}")
    public ResponseEntity<?> deleteAllComment(@PathVariable Long postId) {
        commentService.deleteByPostId(postId);
        return ResponseEntity.ok().build();
    }
}
