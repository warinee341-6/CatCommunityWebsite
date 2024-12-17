package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cat;
import com.example.demo.model.Comment;

@Repository
public interface CatRepository extends CrudRepository<Cat, Long>{
}
