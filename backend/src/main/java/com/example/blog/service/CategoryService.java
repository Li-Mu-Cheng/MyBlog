package com.example.blog.service;

import com.example.blog.dto.CategoryRequest;
import com.example.blog.mapper.CategoryMapper;
import com.example.blog.model.Category;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Cacheable(cacheNames = "categories")
    public List<Category> listCategories() {
        return categoryMapper.findAll();
    }

    public Category getCategory(Long id) {
        return categoryMapper.findById(id);
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryMapper.insert(category);
        return category;
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public Category updateCategory(Long id, CategoryRequest request) {
        Category category = new Category();
        category.setId(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryMapper.update(category);
        return categoryMapper.findById(id);
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void deleteCategory(Long id) {
        categoryMapper.delete(id);
    }
}
