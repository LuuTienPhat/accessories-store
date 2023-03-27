package com.example.demo.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Category;
import com.example.demo.entities.Feedback;
import com.example.demo.entities.Product;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
	private final ProductRepo productRepo;
	private final CategoryRepo categoryRepo;

	@Override
	public Product insertProduct(Product product) {
		Product result = null;
		try {
			log.info("Insert product '{}' ", product.getName());
			result = productRepo.save(product);
		} catch (Exception e) {
			log.info("Failed to insert product '{}' ", product.getName());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Product updateProduct(Long id, Product newProduct) {
		Product updatedProduct = null;
		try {
			log.info("Update product '{}' ", newProduct.getName());
			updatedProduct = productRepo.findById(id).map(product -> {
				product.setName(newProduct.getName());
				product.setPrice(newProduct.getPrice());
				product.setDescription(newProduct.getDescription());
				product.setUnit(newProduct.getUnit());
				product.setCategory(newProduct.getCategory());
				
				return productRepo.save(product);
			}).orElseGet(() -> {
				newProduct.setId(id);
				return productRepo.save(newProduct);
			});
		} catch (Exception e) {
			log.info("Failed to update product '{}' ", newProduct.getName());
			e.printStackTrace();
		}

		return updatedProduct;
	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}

	@Override
	public Product getProduct(Long id) {
		Product product = null;
		try {
			product = productRepo.findById(id).get();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product getProduct(String name) {
		Product product = null;
		try {
			product = productRepo.findByName(name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public List<Product> getProducts() {
		List<Product> products = null;
		try {
			log.info("Fetching all products");
			products = productRepo.findAll();
		} catch (Exception e) {
			log.info("Error while fetching data");
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public List<Product> getProducts(String keyword) {
		List<Product> products = null;
		try {
			log.info("Fetching products with keyword = ?1", keyword);
			products = productRepo.search(keyword);
		} catch (Exception e) {
			log.info("Error while fetching data");
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Long count() {
		return productRepo.count();
	}

	@Override
	public List<Product> getTop5ProductsByViews() {
		return productRepo.findTop5ByOrderByViewsDesc();
	}

	@Override
	public List<Product> getProducts(Integer offset, Integer limit) {
		return productRepo.selectWithOffsetAndLimit(PageRequest.of(offset, limit));
	}

	@Override
	public List<Product> getProductsByCategoryId(Long categoryId) {
		Category category = categoryRepo.findById(categoryId).get();
		return productRepo.findByCategory(category);
	}
	
//	public List<Product> getProductsOrderByViewsDesc() {
//		return productRepo.findTop5ByOrderByViewsDesc();
//	}
}
