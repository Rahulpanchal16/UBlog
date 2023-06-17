package com.blog.services;

import com.blog.payload.UserDto;
import com.blog.payload.UserResponse;


public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	UserResponse getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir);
	void deleteUser(Integer userId);

}
