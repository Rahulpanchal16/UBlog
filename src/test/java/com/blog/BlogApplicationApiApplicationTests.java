package com.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.repositories.UserRepo;

@SpringBootTest
class BlogApplicationApiApplicationTests {

	@Autowired
	private UserRepo repo;
	
	@Test
	void contextLoads() {
	}

	@Test
	void repoTesting() {
		String name = repo.getClass().getName();
		System.out.println(name);
	}
}
