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

import com.blog.payload.ApiResponse;
import com.blog.payload.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// Create User
	@PostMapping(path = "/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	// Update user
	@PutMapping(path = "/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}

	// Delete user
	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable int userId) {
		this.userService.deleteUser(userId);
		return ResponseEntity.ok(new ApiResponse("User deleted Successfully", true));
	}

	// Get single user by its id
	@GetMapping(path = "/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
		UserDto userById = this.userService.getUserById(userId);
		return new ResponseEntity<>(userById, HttpStatus.OK);
	}

//	// Get All Users
//	@GetMapping(path = "/all")
//	public ResponseEntity<UserResponse> getAllUsers(
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
//			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
//			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
//			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
//		UserResponse allUsers = this.userService.getAllUsers(pageSize, pageNumber, sortBy, sortDir);
//		return ResponseEntity.ok(allUsers);
//	}
	@GetMapping(path = "/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> allUsers = this.userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}

}
