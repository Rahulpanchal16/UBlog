package com.blog.payload;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private int postId;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	private String imageName;
	private Date addedDate;
	private UserDto user;
	private CategoryDto category;

}
  