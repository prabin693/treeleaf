package com.restapi.blog.service;

import com.restapi.blog.model.BlogPost;
import com.restapi.blog.model.User;
import com.restapi.blog.repository.BlogPostRepository;
import com.restapi.blog.repository.CommentRepository;
import com.restapi.blog.repository.UserRepository;
import io.jsonwebtoken.Header;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<?> createBlogPost(BlogPost blogPost) {
        Optional<User> user = userRepository.findByUsername(getCurrentUsername());
        if (user.isEmpty()) {
            ResponseEntity.badRequest().body("User not found");
        }
        blogPost.setUser(user.get());
        blogPostRepository.save(blogPost);
        return ResponseEntity.ok("Blog post created successfully");
    }

    private String getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    public ResponseEntity<?> updateBlogPost(BlogPost blogPost) {
        Optional<BlogPost> existingBlogPost = blogPostRepository.findById(blogPost.getId());
        if (existingBlogPost.isEmpty()) {
            ResponseEntity.badRequest().body("Blog post not found");
        }
        Optional<User> user = userRepository.findByUsername(getCurrentUsername());
        if (user.isEmpty()) {
            ResponseEntity.badRequest().body("User not found");
        }
        //check if the user is the owner of the blog post
        if (!existingBlogPost.get().getUser().getUsername().equals(user.get().getUsername())) {
            ResponseEntity.badRequest().body("You are not the owner of this blog post");
        }
        BlogPost updatedBlogPost = existingBlogPost.get();
        updatedBlogPost.setTitle(blogPost.getTitle());
        updatedBlogPost.setContent(blogPost.getContent());
        updatedBlogPost.setUser(user.get());
        blogPostRepository.save(updatedBlogPost);
        return ResponseEntity.ok("Blog post updated successfully");
    }

    public ResponseEntity<?> deleteBlogPost(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isEmpty()) {
            ResponseEntity.badRequest().body("Blog post not found");
        }
        //check if comment exists
        if (commentRepository.findAllByBlogPost(blogPost.get()).size() > 0) {
            ResponseEntity.badRequest().body("Blog post has comments");
        }
        blogPostRepository.deleteById(id);
        return ResponseEntity.ok("Blog post deleted successfully");

    }

    public ResponseEntity<?> getAllBlogPosts() {
        return ResponseEntity.ok(blogPostRepository.findAll());
    }

    public ResponseEntity<?> getBlogPostById(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isEmpty()) {
            ResponseEntity.badRequest().body("Blog post not found");
        }
        return ResponseEntity.ok(blogPost.get());
    }

    public ResponseEntity<?> addImage(MultipartFile file, Long blogPostId) throws IOException {
        Optional<BlogPost> blogPost = blogPostRepository.findById(blogPostId);
        if (blogPost.isEmpty()) {
            ResponseEntity.badRequest().body("Blog post not found");
        }
        System.out.println("File name: " + file.getOriginalFilename());
        BlogPost updatedBlogPost = blogPost.get();
        updatedBlogPost.setImage(file.getBytes());
        blogPostRepository.save(updatedBlogPost);
        return ResponseEntity.ok("Image added successfully");

    }

    public ResponseEntity<?> getImage(Long blogPostId) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(blogPostId);
        if (blogPost.isEmpty()) {
            ResponseEntity.badRequest().body("Blog post not found");
        }
        if (blogPost.get().getImage() == null) {
            ResponseEntity.badRequest().body("Image not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(blogPost.get().getImage());
    }
}
