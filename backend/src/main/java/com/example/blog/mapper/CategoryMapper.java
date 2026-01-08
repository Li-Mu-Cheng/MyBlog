package com.example.blog.mapper;

import com.example.blog.model.Category;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CategoryMapper {
    @Select("""
            SELECT id, name, description, created_at, updated_at
            FROM categories
            ORDER BY created_at DESC
            """)
    List<Category> findAll();

    @Select("""
            SELECT id, name, description, created_at, updated_at
            FROM categories
            WHERE id = #{id}
            """)
    Category findById(Long id);

    @Insert("""
            INSERT INTO categories (name, description, created_at, updated_at)
            VALUES (#{name}, #{description}, NOW(), NOW())
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    @Update("""
            UPDATE categories
            SET name = #{name},
                description = #{description},
                updated_at = NOW()
            WHERE id = #{id}
            """)
    int update(Category category);

    @Delete("""
            DELETE FROM categories
            WHERE id = #{id}
            """)
    int delete(Long id);
}
