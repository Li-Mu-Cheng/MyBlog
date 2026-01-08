package com.example.blog.mapper;

import com.example.blog.model.Post;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PostMapper {
    @Select("""
            <script>
            SELECT id, category_id, title, summary, content, status, created_at, updated_at
            FROM posts
            <where>
                <if test="keyword != null and keyword != ''">
                    (title LIKE CONCAT('%', #{keyword}, '%')
                    OR summary LIKE CONCAT('%', #{keyword}, '%'))
                </if>
            </where>
            ORDER BY created_at DESC
            </script>
            """)
    List<Post> findAll(String keyword);

    @Select("""
            SELECT id, category_id, title, summary, content, status, created_at, updated_at
            FROM posts
            WHERE id = #{id}
            """)
    Post findById(Long id);

    @Insert("""
            INSERT INTO posts (category_id, title, summary, content, status, created_at, updated_at)
            VALUES (#{categoryId}, #{title}, #{summary}, #{content}, #{status}, NOW(), NOW())
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Post post);

    @Update("""
            UPDATE posts
            SET category_id = #{categoryId},
                title = #{title},
                summary = #{summary},
                content = #{content},
                status = #{status},
                updated_at = NOW()
            WHERE id = #{id}
            """)
    int update(Post post);

    @Delete("""
            DELETE FROM posts
            WHERE id = #{id}
            """)
    int delete(Long id);
}
