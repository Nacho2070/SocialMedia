package com.PostService.PostService.persistence.entity;

import  jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "blog_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogPostId;

    private String authorId;

    private String imagePath;

    private String title;

    private Date postDate;

    private String content;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();
}
