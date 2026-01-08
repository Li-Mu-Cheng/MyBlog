package com.example.blog.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long postId;
    private String author;
    private String email;
    private String content;
    private LocalDateTime createdAt;
}
