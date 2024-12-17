package com.example.demo.repository;

public class CommentNotFoundException extends RuntimeException{

	public  CommentNotFoundException(Long id) {
		super("Could not found comment "+id);
	}
}
