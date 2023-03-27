package com.example.demo.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Category;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepo categoryRepo;

	@Override
	public Category insertCategory(Category category) {
		Category result = null;
		try {
			log.info("Saving new category '{}' to the database", category.getName());
			result = categoryRepo.save(category);
		} catch (Exception e) {
			log.info("Error while inserting category '{}' to the database", category.getName());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Category updateCategory(Long id, Category newCategory) {
		Category updatedCategory = null;
		try {
			log.info("Updating category '{}' to the database", newCategory.getName());
			updatedCategory = categoryRepo.findById(id).map(category -> {
				category.setName(newCategory.getName());
				category.setDescription(newCategory.getDescription());
				category.setStatus(newCategory.getStatus());
				
				return categoryRepo.save(category);
			}).orElseGet(() -> {
				newCategory.setId(id);
				return categoryRepo.save(newCategory);
			});
		} catch (Exception e) {
			log.info("Error while updating category '{}' to the database", newCategory.getName());
			e.printStackTrace();
		}

		return updatedCategory;
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public Category getCategory(Long id) {
		Category category = null;
		try {
			category = categoryRepo.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public Category getCategory(String name) {
		Category category = null;
		try {
			category = categoryRepo.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public List<Category> getCategories() {
		List<Category> categories = null;
		try {
			categories = categoryRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public Long count() {
		return categoryRepo.count();
	}

	@Override
	public List<Category> getCategories(String keyword) {
		List<Category> categories = null;
		try {
			categories = categoryRepo.search(keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public List<Category> getCategories(Integer offset, Integer limit) {
		return categoryRepo.selectWithOffsetAndLimit(PageRequest.of(offset, limit));
	}

//	@Override
//	public List<Category> getCategories(int from, int limit) {
//		// TODO Auto-generated method stub
//		Page<Category> pages = categoryRepo.findAll(PageRequest.of(from, limit));
//		return pages.toList();
//	}
}
