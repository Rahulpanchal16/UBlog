package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
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

//	private PostDto updatedPostDto;

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

		Post postById = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		postById.setImageName(postDto.getImageName());
		postById.setContent(postDto.getContent());
		postById.setTitle(postDto.getTitle());

		Post updatedPost = this.postRepo.save(postById);
		PostDto dto = this.modelMapper.map(updatedPost, PostDto.class);
		return dto;
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
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> list = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(list);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		// postResponse.set
//		List<PostDto> posts = new ArrayList<>();
//		for (Post post : allPosts) {
//
//			PostDto post1 = this.modelMapper.map(post, PostDto.class);
//			posts.add(post1);
//		}
		return postResponse;

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

//	@Override
//	public PostDto createTenPosts(PostDto postDto, int userId, int catId) {
//
//		User user = this.userRepo.findById(userId)
//				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
//		Category category = this.categoryRepo.findById(catId)
//				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
//
//		for (int i = 1; i <= 10; i++) {
//			Post post = this.modelMapper.map(postDto, Post.class);
//			post.setCategory(category);
//			post.setUser(user);
//			post.setTitle("Title: " + i);
//			post.setContent("Content: " + i);
//			post.setImageName("default.png");
//			post.setAddedDate(new Date());
//			Post savedPost = this.postRepo.save(post);
//			updatedPostDto = this.modelMapper.map(savedPost, PostDto.class);
//
//		}
//
//		return updatedPostDto;
//	}

}
