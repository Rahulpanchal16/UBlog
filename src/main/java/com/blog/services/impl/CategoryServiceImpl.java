package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto catDto) {

		Category category = this.modelMapper.map(catDto, Category.class);
		Category createdCategory = this.categoryRepo.save(category);

		return this.modelMapper.map(createdCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto catDto, int catId) {

		Category categoryById = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Categor", " Id ", catId));
		categoryById.setCategoryTitle(catDto.getCategoryTitle());
		categoryById.setCategoryDescription(catDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(categoryById);

		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int catId) {
		Category catById = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Id", catId));
		this.categoryRepo.delete(catById);
	}

	@Override
	public CategoryDto getCategoryById(int catId) {
		Category category = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Id", catId));
		CategoryDto catById = this.modelMapper.map(category, CategoryDto.class);
		return catById;
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCategory = this.categoryRepo.findAll();
		List<CategoryDto> categoryList = allCategory.stream()
				.map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryList;
	}

//	private Category categoryDtoToCategory(CategoryDto catDto) {
//		return this.modelMapper.map(catDto, Category.class);
//	}

}
