package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Cat;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.CatService;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

@Controller
public class CatController {

	@Autowired
	private UserService userService;


	@Autowired
	CatService catService;

	@GetMapping("/addCat")
	public String addCatForm(Model model) {
		model.addAttribute("cat", new Cat());
		return "addCat";
	}

	@PostMapping("/addCat")
	public String addCat(@ModelAttribute Cat cat, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		cat.setUser(user);
		catService.saveCat(cat);
		return "redirect:/myProfile";
	}

	@GetMapping("/editCat/{id}")
	public String editCatForm(@PathVariable Long id, Model model) {
		Cat cat = catService.getCatById(id);
		model.addAttribute("cat", cat);
		return "editCat";
	}

	@PostMapping("/updateCat/{id}")
	public String updateCat(@PathVariable Long id, @ModelAttribute Cat cat, Model model) {
		catService.updateCat(id, cat);
		return "redirect:/myProfile";
	}

	@GetMapping("/deleteCat/{id}")
	public String deleteCat(@PathVariable Long id) {
		catService.deleteCat(id);
		return "redirect:/myProfile";
	}
	
	@GetMapping("/catDetail/{id}")
	public String viewCat(@PathVariable Long id, Model model, Principal principal) {
		User myUser = userService.findByUsername(principal.getName());
	    Cat cat = catService.getCatById(id);
	    User user = cat.getUser();
	    model.addAttribute("cat", cat);
	    model.addAttribute("user", user);
	    
	    if(myUser.getId()==user.getId()) {
	    	return "viewCatDetailbyUser";
	    }else {
	    	return "viewCatDetail";
	    }

	    
	}

}
