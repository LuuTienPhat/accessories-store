package com.example.demo.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Category;
import com.example.demo.models.ResponseObject;
import com.example.demo.service.CategoryService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> allButLimit(@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "search", required = false) String keyword) {
		ResponseEntity<ResponseObject> responseEntity = null;

		List<Category> result = null;
		;
		if (keyword != null) {
			result = categoryService.getCategories(keyword);
		}
		else if (offset != null && limit != null){
			result = categoryService.getCategories(offset, limit);
		} 
		else {
			result = categoryService.getCategories();
		}
		
		if (result != null) {
			
			result.removeIf(item -> item.getStatus().equalsIgnoreCase("VOID"));
			
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch categories successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch categories!", result));
		}
		
		

		return responseEntity;
	}

	@PostMapping(value = "")
	public ResponseEntity<ResponseObject> newCategory(@RequestBody FormCategory formCategory) {
		ResponseEntity<ResponseObject> responseEntity = null;

		Category newCategory = new Category();
		newCategory.setDescription(formCategory.getDescription());
		newCategory.setName(formCategory.getName());
		newCategory.setStatus("NEW");

		Category result = categoryService.insertCategory(newCategory);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Insert category successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to insert category!", result));
		}

		return responseEntity;

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> one(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		Category result = categoryService.getCategory(id);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch category successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Category not found!", result));
		}

		return responseEntity;
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> replaceCategory(@RequestBody FormCategory formCategory,
			@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;

		Category newCategory = categoryService.getCategory(id);
		newCategory.setDescription(formCategory.getDescription());
		newCategory.setName(formCategory.getName());
		
		Category result = categoryService.updateCategory(id, newCategory);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Update category successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to update category!", result));
		}

		return responseEntity;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		try {
			
			Category newCategory = categoryService.getCategory(id);
			newCategory.setStatus("VOID");
			
			categoryService.updateCategory(id, newCategory);
			
			//categoryService.deleteCategory(id);
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Delete category successfully!", null));
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to delete category!", null));
		}

		return responseEntity;
	}

	@GetMapping(value = "/count")
	@ResponseBody
	ResponseEntity<ResponseObject> count() {
		Long result = categoryService.count();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", HttpStatus.OK.value(), "Category count", result));

	}
}

@Data
class FormCategory {
	Long id;
	String name;
	String description;
}
