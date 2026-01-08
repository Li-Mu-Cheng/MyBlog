package com.example.blog.dto;

import lombok.Data;

@Data
public class PostRequest {
    private Long categoryId;
    private String title;
    private String summary;
    private String content;
    private String status;
}
