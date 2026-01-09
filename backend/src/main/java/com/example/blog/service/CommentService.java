package com.example.blog.service;

import com.example.blog.dto.CommentRequest;
import com.example.blog.mapper.CommentMapper;
import com.example.blog.model.Comment;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public List<Comment> listComments(Long postId) {
        return commentMapper.findByPostId(postId);
    }

    public Comment createComment(Long postId, CommentRequest request) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAuthor(request.getAuthor());
        comment.setEmail(request.getEmail());
        comment.setContent(request.getContent());
        commentMapper.insert(comment);
        return comment;
    }

    public void deleteComment(Long id) {
        commentMapper.delete(id);
    }
}
