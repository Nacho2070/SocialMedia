package com.PostService.PostService.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(nullable = false)
    private Long userLikeToId;
}
