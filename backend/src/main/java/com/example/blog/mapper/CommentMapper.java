package com.example.blog.mapper;

import com.example.blog.model.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {
    @Select("""
            SELECT id, post_id, author, email, content, created_at
            FROM comments
            WHERE post_id = #{postId}
            ORDER BY created_at DESC
            """)
    List<Comment> findByPostId(Long postId);

    @Insert("""
            INSERT INTO comments (post_id, author, email, content, created_at)
            VALUES (#{postId}, #{author}, #{email}, #{content}, NOW())
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comment comment);

    @Delete("""
            DELETE FROM comments
            WHERE id = #{id}
            """)
    int delete(Long id);
}
