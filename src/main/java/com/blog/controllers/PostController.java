package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.PostDto;
import com.blog.services.PostService;

@RestController
@RequestMapping(path = "/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping(path = "/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId,
			@PathVariable int catId) {
		PostDto createPost = postService.createPost(postDto, userId, catId);
		return ResponseEntity.status(HttpStatus.CREATED).body(createPost);
	}

	@GetMapping(path = "/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable int userId) {
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		return ResponseEntity.ok(postsByUser);
	}

	@GetMapping(path = "/category/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable int catId) {
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(catId);
		return ResponseEntity.ok(postsByCategory);
	}

	@GetMapping(path = "/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
		PostDto postById = this.postService.getPostById(postId);
		return ResponseEntity.ok(postById);
	}

	@GetMapping(path = "/posts/")
	public ResponseEntity<List<PostDto>> getAllPosts() {
		List<PostDto> allPosts = this.postService.getAllPosts();
		return ResponseEntity.ok(allPosts);
	}

	@DeleteMapping(path = "/posts/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable int postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
	}

	@PutMapping(path = "/posts/{postId}")
	public ResponseEntity<PostDto> updateDto(@RequestBody PostDto postDto, @PathVariable int postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatePost);
	}

}
