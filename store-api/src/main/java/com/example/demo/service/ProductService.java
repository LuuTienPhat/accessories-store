package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;

public interface ProductService {
	Product insertProduct(Product Product);

	Product updateProduct(Long id, Product newProduct);

	void deleteProduct(Long id);

	Product getProduct(Long id);

	Product getProduct(String word);
	
	List<Product> getProducts();
	
	List<Product> getTop5ProductsByViews();
	
	List<Product> getProducts(String keyword);
	
	List<Product> getProducts(Integer offset, Integer limit);
	
	List<Product> getProductsByCategoryId(Long categoryId);
	
	Long count();
}
