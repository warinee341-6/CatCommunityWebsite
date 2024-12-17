package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

@Controller
public class CommentController {
	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;

	@PostMapping("/addComment/{postId}")
	public String addComment(@PathVariable("postId") Long postId, @ModelAttribute Comment comment,
			Principal principal) {
		// Get the current post and user
		Post currentPost = postService.getPostById(postId);
		User currentUser = userService.findByUsername(principal.getName());

		// Associate the comment with the post and user
		comment.setPost(currentPost);
		comment.setUser(currentUser);

		// Save the comment
		commentService.saveComment(comment);

		// Redirect back to the post view
		return "redirect:/viewPost/" + postId;
	}

	@GetMapping("/editComment/{id}")
	public String editComment(@PathVariable Long id, Model model) {
		Comment comment = commentService.getCommentById(id);
		model.addAttribute("comment", comment);
		return "editComment"; // Create an editComment.html template
	}


	@GetMapping("/deleteComment/{id}")
	public String deleteComment(@PathVariable Long id) {
		Comment comment = commentService.getCommentById(id);
		Long postId = comment.getPost().getId();
		commentService.deleteComment(id);
		return "redirect:/viewPost/" + postId;
	}
}
