package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.UserDto;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	PostService postService;

	@GetMapping("/")
	public String home(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login"; // หากยังไม่ได้ล็อกอิน ให้ redirect ไปที่หน้า login
		}
		UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", user);
		List<User> userList = userService.getUsers().stream().filter(u -> u.getUsername() != null)
				.collect(Collectors.toList());

		model.addAttribute("users", userList);
		List<Post> posts = postService.getPosts();
		model.addAttribute("posts", posts);
		return "home";
	}

	@GetMapping("/login")
	public String login(Model model, UserDto userDto) {
		model.addAttribute("user", userDto);
		return "login";
	}

	@GetMapping("/register")
	public String register(Model model, UserDto userDto) {
		model.addAttribute("user", userDto);
		return "register";
	}

	@PostMapping("/register")
	public String registerSave(@ModelAttribute("user") UserDto userDto, Model model) {
		User user = userService.findByUsername(userDto.getUsername());
		if (user != null) {
			model.addAttribute("Userexist", user);
			return "register";
		}
		userService.save(userDto);
		return "redirect:/register?success";
	}

	// View Profile
	@GetMapping("/user/{id}")
	public String getViewProfile(@PathVariable Long id, Model model, Principal principal) {
		User myUser = userService.findByUsername(principal.getName());
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("myUser", myUser);
		return "viewUser";
	}

	@GetMapping("/editUser/{id}")
	public String editUser(@PathVariable Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "editUser";
	}



	@PostMapping("/updateUser/{id}")
	public String updateUser(@PathVariable Long id, @ModelAttribute User user,
			@ModelAttribute("currentPassword") String currentPassword, Principal principal, HttpSession session,
			Model model) {
		User myUser = userService.findByUsername(principal.getName());

		if (myUser.getId() == id) {
			if (userService.checkPassword(myUser, currentPassword)) {
				userService.updateUser(id, user);
				session.setAttribute("user", userService.getUserById(id));
				return "redirect:/myProfile";
			} else {
				model.addAttribute("passwordError", "Current password is incorrect.");
				return "editUser";
			}
		} else {
			return "error/403";
		}
	}
	

}