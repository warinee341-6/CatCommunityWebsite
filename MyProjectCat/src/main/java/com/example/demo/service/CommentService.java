package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentNotFoundException;
import com.example.demo.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
	public List<Comment> getCommentsByPostId(Long postId) {
	    return commentRepository.findByPostId(postId);
	}
	public List<Comment> getComments(){
		List<Comment> comments = (List<Comment>) commentRepository.findAll();
		return comments;
	}
	
	public Comment getCommentById(Long id) {
		return commentRepository.findById(id).orElseThrow(()->new CommentNotFoundException(id));
	}

	public void saveComment(Comment c) {
		commentRepository.save(c);
	}
	
	public Comment addComment(Comment c) {
		commentRepository.save(c);
		return c;
	}
	
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}
	
	public Comment updateComment(Long id, Comment c) {
		Comment existingComment = commentRepository.findById(id).get();
		existingComment.setContent(c.getContent());
		return commentRepository.save(existingComment);
	}
}
