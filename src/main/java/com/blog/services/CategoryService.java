package com.blog.services;

import java.util.List;

import com.blog.payload.CategoryDto;


public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto catDto);
	
	//update
	CategoryDto updateCategory(CategoryDto catDto,int catId);
	
	//delete
	void deleteCategory(int catId);
	
	//get single category
	CategoryDto getCategoryById(int catId);
	
	//get All categories
	List<CategoryDto> getAllCategories();

}
