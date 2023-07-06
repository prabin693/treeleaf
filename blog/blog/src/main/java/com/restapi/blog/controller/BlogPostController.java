package com.restapi.blog.controller;

import com.restapi.blog.model.BlogPost;
import com.restapi.blog.service.BlogPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BlogPostController {
    private final BlogPostService blogPostService;

    //endpoint for creating a blog post
    @PostMapping("/user/blogpost")
    public ResponseEntity<?> createBlogPost(@RequestBody BlogPost blogPost) {
        return blogPostService.createBlogPost(blogPost);
    }

    //add image
    @PostMapping("/user/blogpost/image/{blogPostId}")
    public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file, @PathVariable("blogPostId") Long blogPostId) throws IOException {
        return blogPostService.addImage(file, blogPostId);
    }

    //get image
    @GetMapping("/user/blogpost/image/{blogPostId}")
    public ResponseEntity<?> getImage(@PathVariable Long blogPostId) throws IOException {
        return blogPostService.getImage(blogPostId);
    }

    //endpoint for updating a blog post
    @PutMapping("/user/blogpost/update")
    public ResponseEntity<?> updateBlogPost(@RequestBody BlogPost blogPost) {
        return blogPostService.updateBlogPost(blogPost);
    }

    //endpoint for deleting a blog post
    @DeleteMapping("/admin/blogpost/delete/{id}")
    public ResponseEntity<?> deleteBlogPost(@PathVariable Long id) {
        return blogPostService.deleteBlogPost(id);
    }

    //endpoint for getting all blog posts
    @GetMapping("/user/blogpost/all")
    public ResponseEntity<?> getAllBlogPosts() {
        return blogPostService.getAllBlogPosts();
    }

    //endpoint for getting a blog post by id
    @GetMapping("/user/blogpost/{id}")
    public ResponseEntity<?> getBlogPostById(@PathVariable Long id) {
        return blogPostService.getBlogPostById(id);
    }
}
