package com.blog.services;

import java.util.List;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, int userId, int catId);

	// update
	PostDto updatePost(PostDto postDto, int postId);

	// delete
	void deletePost(int postId);

	// get single
	PostDto getPostById(int postId);

	// get all
	PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir);

	// get posts by category
	List<PostDto> getPostsByCategory(int catId);

	// get posts by user
	List<PostDto> getPostsByUser(int userId);

	// search posts
	List<PostDto> searchPosts(String keyword);

	// creating 10 test posts
//	PostDto createTenPosts(PostDto postDto,int userId,int catId);

}
