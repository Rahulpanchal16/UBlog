package com.blog.services;

import com.blog.payload.CategoryDto;
import com.blog.payload.CategoryResponse;

public interface CategoryService {

	// create
	CategoryDto createCategory(CategoryDto catDto);

	// update
	CategoryDto updateCategory(CategoryDto catDto, int catId);

	// delete
	void deleteCategory(int catId);

	// get single category
	CategoryDto getCategoryById(int catId);

	// get All categories
	CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);

}
