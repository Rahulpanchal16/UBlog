package com.blog.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
		info = @Info(
				title="UBlog",
				version = "1.0.0",
				description = "This is a complete backend for a blog application build using only spring boot",
				termsOfService = "UBlog",
				contact = @Contact(
						name = "Rahul Panchal",
						email = "panchalrahul180@gmail.com"
						),
				license = @License(
						name = "UBlog licence",
						url = "wwww.ublog.com"
						)
				)
		)
public class SwaggerConfig {
}
