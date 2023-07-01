package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping(path = "/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping(path = "/posts/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto cmtDto, @PathVariable Integer postId) {

		CommentDto createdComment = this.commentService.createComment(postId, cmtDto);
		return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/comment/commentId")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer postID) {

		this.commentService.deleteComment(postID);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);
	}

}
