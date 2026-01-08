package com.example.blog.service;

import com.example.blog.dto.PostRequest;
import com.example.blog.mapper.PostMapper;
import com.example.blog.model.Post;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Cacheable(cacheNames = "posts", key = "#keyword == null ? 'all' : #keyword")
    public List<Post> listPosts(String keyword) {
        return postMapper.findAll(keyword);
    }

    @Cacheable(cacheNames = "post", key = "#id")
    public Post getPost(Long id) {
        return postMapper.findById(id);
    }

    @CacheEvict(cacheNames = {"posts", "post"}, allEntries = true)
    public Post createPost(PostRequest request) {
        Post post = new Post();
        post.setCategoryId(request.getCategoryId());
        post.setTitle(request.getTitle());
        post.setSummary(request.getSummary());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus() == null ? "PUBLISHED" : request.getStatus());
        postMapper.insert(post);
        return post;
    }

    @CacheEvict(cacheNames = {"posts", "post"}, allEntries = true)
    public Post updatePost(Long id, PostRequest request) {
        Post post = new Post();
        post.setId(id);
        post.setCategoryId(request.getCategoryId());
        post.setTitle(request.getTitle());
        post.setSummary(request.getSummary());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());
        postMapper.update(post);
        return postMapper.findById(id);
    }

    @CacheEvict(cacheNames = {"posts", "post"}, allEntries = true)
    public void deletePost(Long id) {
        postMapper.delete(id);
    }
}
