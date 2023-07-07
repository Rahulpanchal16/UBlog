package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.configs.AppConstants;
import com.blog.payload.ApiResponse;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

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

	@GetMapping(path = "/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(allPosts);
	}

	@DeleteMapping(path = "/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
		this.postService.deletePost(postId);
		ApiResponse apiResponse = new ApiResponse("Post is deleted successfully", true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping(path = "/posts/{postId}")
	public ResponseEntity<PostDto> updateDto(@RequestBody PostDto postDto, @PathVariable int postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatePost);
	}

	@GetMapping(path = "/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> search(@PathVariable String keyword) {

		List<PostDto> searchPosts = this.postService.searchPosts(keyword);

		return ResponseEntity.ok(searchPosts);
	}

	@PostMapping(path = "/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile file,
			@PathVariable Integer postId) throws IOException {

		PostDto postById = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, file);

		postById.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postById, postId);

		return ResponseEntity.ok(updatedPost);

	}

	@GetMapping(path = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getPostImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {

		InputStream inputStream = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());
	}

//	@PostMapping(path = "/user/{userId}/category/{catId}/ten-posts")
//	public ResponseEntity<PostDto> createTenPost(@RequestBody PostDto postDto, @PathVariable int userId,
//			@PathVariable int catId) {
//		PostDto createPost = postService.createTenPosts(postDto, userId, catId);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createPost);
//	}

}
