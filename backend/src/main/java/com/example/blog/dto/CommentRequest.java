package com.example.blog.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private String author;
    private String email;
    private String content;
}
