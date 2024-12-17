package com.example.demo.repository;

public class CatNotFoundException extends RuntimeException{
	
	public  CatNotFoundException(Long id) {
		super("Could not found cat "+id);
	}

}
