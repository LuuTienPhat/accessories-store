package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.UserProduct;

public interface UserProductService {
	UserProduct insertCart(UserProduct Cart);

	UserProduct updateCart(Long id, UserProduct newCart);

	void deleteCart(Long id);
	
	void deleteCartByProductId(Long productId);
	
	List<UserProduct> findAllByType(String type);
//
//	UserProduct getCart(Long id);
//	
	UserProduct getCartByProductId(Long productId);
	
	UserProduct getUserProductByProductIdAndUserIdAndType(Long productId, Long userId, String type);
	
//	UserProduct getCartByProductId(Long id);

	UserProduct getCart(String word);
	
//	List<UserProduct> getCategories();
//	
//	List<UserProduct> getCategories(String keyword);
	
	Long count();
	
	UserProduct getById(Long id);
	
	List<UserProduct> getUserProductsByUserIdAndType(Long userId, String type);
	
	List<UserProduct> getUserProductsByProductIdAndType(Long productId, String type);
	
	//List<UserProduct> getUserProductsByProductIdAndUserIdAndType(Long productId, Long userId, String type);
}
