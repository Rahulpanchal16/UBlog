package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.User;
import com.blog.payload.UserResponse;


public interface UserRepo extends JpaRepository<User, Integer> {

	UserResponse findByNameContaining(String name);
}
