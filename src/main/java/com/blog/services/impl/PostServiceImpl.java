package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, int userId, int catId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category category = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		post.setImageName("default.png");
		Post savedPost = this.postRepo.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {

		Post post = this.modelMapper.map(postDto, Post.class);
		Post postById = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		postById.setCategory(post.getCategory());
		postById.setContent(post.getContent());

		return null;
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> allPosts = this.postRepo.findAll();
		List<PostDto> list = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
//		List<PostDto> posts = new ArrayList<>();
//		for (Post post : allPosts) {
//
//			PostDto post1 = this.modelMapper.map(post, PostDto.class);
//			posts.add(post1);
//		}
		return list;

	}

	@Override
	public List<PostDto> getPostsByCategory(int catId) {
		Category category = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", catId));
		List<Post> postsByCategory = this.postRepo.findByCategory(category);
		List<PostDto> list = postsByCategory.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<PostDto> getPostsByUser(int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		List<Post> findByUser = this.postRepo.findByUser(user);
		List<PostDto> collect = findByUser.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {

		return null;
	}

}
