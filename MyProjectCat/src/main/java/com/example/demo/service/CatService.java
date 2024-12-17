package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cat;
import com.example.demo.model.Comment;
import com.example.demo.repository.CatNotFoundException;
import com.example.demo.repository.CatRepository;

@Service
public class CatService {
	
	@Autowired
	CatRepository catRepository;
	

	public List<Cat> getCats(){
		List<Cat> cats = (List<Cat>) catRepository.findAll();
		return cats;
	}
	
	public Cat getCatById(Long id) {
		return catRepository.findById(id).orElseThrow(()->new CatNotFoundException(id));
	}
	
	public void saveCat(Cat cat) {
		catRepository.save(cat);
	}
	
	public Cat addCat(Cat cat) {
		catRepository.save(cat);
		return cat;
	}
	
	public void deleteCat(Long id) {
		catRepository.deleteById(id);
	}
	
	public Cat updateCat(Long id, Cat cat) {
		Cat existingCat = catRepository.findById(id).get();
		existingCat.setName(cat.getName());
		existingCat.setAge(cat.getAge());
		existingCat.setBreed(cat.getBreed());
		existingCat.setCatPic(cat.getCatPic());
		return catRepository.save(existingCat);
	}
	
}
