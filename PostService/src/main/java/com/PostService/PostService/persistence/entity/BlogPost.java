package com.PostService.PostService.persistence.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.*;

@Document(collation = "blogPost")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPost implements Serializable {

    @MongoId(FieldType.OBJECT_ID)
    private String blogPostId;

    private String authorId;

    private String imagePath;

    private String title;

    private Date postDate;

    private String content;

    private LinkedList<Long> commentID = new LinkedList<>();

}
