package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	// searching by title
	List<Post> findByTitleContaining(String title);

	// searching by title approach 2
//	@Query("select p from Post p where p.title like :key")
//	List<Post> search(@Param("key") String title);
}
