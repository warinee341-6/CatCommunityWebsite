package com.example.demo.repository;

public class PostNotFoundException extends RuntimeException{

	public  PostNotFoundException(Long id) {
		super("Could not found Post "+id);
	}
}
