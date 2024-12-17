package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostNotFoundException;
import com.example.demo.repository.PostRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;


    public List<Post> getPosts() {
        return (List<Post>) postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post addPost(Post post) {
        postRepository.save(post);
        return post;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post updatePost(Long id, Post post) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setPostPic(post.getPostPic());
        return postRepository.save(existingPost);
    }
}
