package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

@Controller
public class PostController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;


	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;


	@GetMapping("/myProfile")
	public String showProfile(@ModelAttribute Post post, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "myProfile";
	}

	@GetMapping("/addPost")
	public String addPostForm(Model model) {
		model.addAttribute("post", new Post());
		return "addPost";
	}

	@PostMapping("/addPost")
	public String addPost(@ModelAttribute Post post, Principal principal) {
		// รับผู้ใช้ที่ล็อกอินอยู่
		User user = userService.findByUsername(principal.getName());

		// ตั้งค่าผู้ใช้ของโพสต์และบันทึกโพสต์
		post.setUser(user);
		postService.savePost(post);
		return "redirect:/";
	}

	@GetMapping("/editPost/{id}")
	public String editPostForm(@PathVariable Long id, Model model) {
		Post post = postService.getPostById(id);
		model.addAttribute("post", post);
		return "editPost";
	}

	@PostMapping("/updatePost/{id}")
	public String updatePost(@PathVariable Long id, @ModelAttribute Post post, Model model) {
		postService.updatePost(id, post);
		return "redirect:/myProfile";
	}

	@GetMapping("/deletePost/{id}")
	public String deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return "redirect:/myProfile";
	}
	
	@GetMapping("/viewPost/{id}")
	public String viewPost(@PathVariable Long id, Model model, Principal principal) {
	    Post post = postService.getPostById(id);
	    List<Comment> comments = commentService.getCommentsByPostId(id);
	    User user = post.getUser();
	    //ใช้เอาไว้เช็คว่า เจ้าของคอมเมนท์คือคนที่login อยู่เพื่อใช้สิทธิ์delete
	    User myUser = userService.findByUsername(principal.getName());
	    model.addAttribute("myUser", myUser);
	    
	    model.addAttribute("post", post);
	    model.addAttribute("comments", comments);
	    model.addAttribute("user", user); 

	    return "viewPost";
	}


}
