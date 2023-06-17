package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.payload.CategoryResponse;
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
	public CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = ((sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()));

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Category> pageCategory = this.categoryRepo.findAll(pageable);
		List<Category> cat = pageCategory.getContent();
		List<CategoryDto> categoryList = cat.stream()
				.map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(categoryList);
		categoryResponse.setPageNumber(pageCategory.getNumber());
		categoryResponse.setPageSize(pageCategory.getSize());
		categoryResponse.setTotalElements(pageCategory.getTotalElements());
		categoryResponse.setTotalPages(pageCategory.getTotalPages());
		categoryResponse.setLastPage(pageCategory.isLast());

		return categoryResponse;
	}

//	private Category categoryDtoToCategory(CategoryDto catDto) {
//		return this.modelMapper.map(catDto, Category.class);
//	}

}
