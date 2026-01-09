package com.example.blog.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Category {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
