package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping(path = "/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto) {
		CategoryDto createCategory = this.categoryService.createCategory(catDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto,
			@PathVariable("catId") int catId) {
		CategoryDto updateCategory = this.categoryService.updateCategory(catDto, catId);
		return ResponseEntity.ok(updateCategory);
	}

	@DeleteMapping(path = "/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") int catId) {
		this.categoryService.deleteCategory(catId);
		return ResponseEntity.ok(new ApiResponse("Category deleted successfully", true));
	}

	@GetMapping(path = "/{catId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("catId") int catId) {
		CategoryDto categoryById = this.categoryService.getCategoryById(catId);
		return ResponseEntity.ok(categoryById);
	}

	@GetMapping(path = "/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> allCategories = this.categoryService.getAllCategories();
		return ResponseEntity.ok(allCategories);
	}

}