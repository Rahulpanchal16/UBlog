package com.blog.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.UserDto;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {

		User dtoToUser = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(dtoToUser);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// User toUser = this.dtoToUser(userDto);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "  Id ", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));
		return this.userToDto(user);
	}

//	@Override
//	public UserResponse getAllUsers(int pageSize, int pageNumber, String sortBy, String sortDir) {
//
//		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
//		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//
//		Page<User> pageUser = this.userRepo.findAll(pageable);
//		List<User> content = pageUser.getContent();
//		List<UserDto> list = content.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
//		UserResponse userResponse = new UserResponse();
//		userResponse.setContent(list);
//		userResponse.setPageSize(pageUser.getSize());
//		userResponse.setPageNumber(pageUser.getNumber());
//		userResponse.setTotalElements(pageUser.getTotalElements());
//		userResponse.setTotalPages(pageUser.getTotalPages());
//		userResponse.setLastPage(pageUser.isLast());
//
//		return userResponse;
//	}
	public List<UserDto> getAllUsers() {
		List<User> findAll = this.userRepo.findAll();
		List<UserDto> list = findAll.stream().map(x->this.modelMapper.map(x, UserDto.class)).toList();
		return list;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id ", userId));
		this.userRepo.delete(user);

	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		/*
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setPassword(userDto.getPassword());
		 * user.setAbout(userDto.getAbout());
		 */
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		/*
		 * userDto.setId(user.getId()); userDto.setName(user.getName());
		 * userDto.setEmail(user.getEmail()); userDto.setPassword(user.getPassword());
		 * userDto.setAbout(user.getAbout());
		 */
		return userDto;
	}

}
