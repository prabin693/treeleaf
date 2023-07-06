package com.restapi.blog.controller;

import com.restapi.blog.model.Comment;
import com.restapi.blog.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    //endpoint for creating a comment
    @PostMapping("/user/comment/create/{blogPostId}")
    public ResponseEntity<?> createComment(@RequestBody Comment comment, @PathVariable Long blogPostId) {
        return commentService.createComment(comment, blogPostId);
    }

    //endpoint for updating a comment
    @PostMapping("/user/comment/update")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment) {
        return commentService.updateComment(comment);
    }

    //endpoint for deleting a comment
    @PostMapping("/admin/comment/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    //endpoint for getting all comments for a blog post
    @PostMapping("/user/comment/all/{blogPostId}")
    public ResponseEntity<?> getAllCommentsForBlogPost(@PathVariable Long blogPostId) {
        return commentService.getAllCommentsForBlogPost(blogPostId);
    }
}
