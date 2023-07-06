package com.restapi.blog.service;

import com.restapi.blog.model.BlogPost;
import com.restapi.blog.model.Comment;
import com.restapi.blog.repository.BlogPostRepository;
import com.restapi.blog.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogPostRepository blogPostRepository;
    public ResponseEntity<?> createComment(Comment comment, Long blogPostId) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(blogPostId);
        if (blogPost.isEmpty()) {
            ResponseEntity.badRequest().body("Blog post not found");
        }
        comment.setBlogPost(blogPost.get());
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment created successfully");
    }

    public ResponseEntity<?> updateComment(Comment comment) {
        Optional<Comment> existingComment = commentRepository.findById(comment.getId());
        if (existingComment.isEmpty()) {
            ResponseEntity.badRequest().body("Comment not found");
        }
        Comment updatedComment = existingComment.get();
        updatedComment.setContent(comment.getContent());
        commentRepository.save(updatedComment);
        return ResponseEntity.ok("Comment updated successfully");
    }

    public ResponseEntity<?> deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            ResponseEntity.badRequest().body("Comment not found");
        }
        commentRepository.delete(comment.get());
        return ResponseEntity.ok("Comment deleted successfully");
    }

    public ResponseEntity<?> getAllCommentsForBlogPost(Long blogPostId) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(blogPostId);
        if (blogPost.isEmpty()) {
            ResponseEntity.badRequest().body("Blog post not found");
        }
        return ResponseEntity.ok(commentRepository.findAllByBlogPost(blogPost.get()));
    }
}
