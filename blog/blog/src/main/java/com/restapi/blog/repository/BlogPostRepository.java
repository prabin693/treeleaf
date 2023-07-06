package com.restapi.blog.repository;


import com.restapi.blog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    //get all blog posts by user id
    @Query("SELECT b FROM BlogPost b WHERE b.user.id = ?1")
    Iterable<BlogPost> findAllByUserId(Long userId);
}