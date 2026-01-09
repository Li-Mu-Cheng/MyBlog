package com.example.blog.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Post {
    private Long id;
    private Long categoryId;
    private String title;
    private String summary;
    private String content;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
