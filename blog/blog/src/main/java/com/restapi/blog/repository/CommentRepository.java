package com.restapi.blog.repository;


import com.restapi.blog.model.BlogPost;
import com.restapi.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.blogPost = ?1")
    List<Comment> findAllByBlogPost(BlogPost blogPost);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Comment c WHERE c.blogPost = ?1")
    boolean existsByBlogPost(BlogPost blogPost);
}
