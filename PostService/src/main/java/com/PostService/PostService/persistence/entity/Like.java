package com.PostService.PostService.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    private Long userLikeToId;
}
