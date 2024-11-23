package com.CommentsService.CommentsService.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "commenter_id")
    private Long commenterId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

}
