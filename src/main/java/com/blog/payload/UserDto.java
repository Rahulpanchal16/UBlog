package com.blog.payload;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min = 5,max = 50)
	private String name;
	@NotEmpty
	@Email
	@Pattern(regexp = ".+@.+\\.[a-z]+",message = "Invalid Email address")
	private String email;
	@NotEmpty
	@Size(min = 5, max = 20, message = "Password should be minimum 5 and maximum 20 characters long")
	private String password;
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();


}
