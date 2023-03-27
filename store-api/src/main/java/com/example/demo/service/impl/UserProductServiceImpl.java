package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderDetail;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProduct;
import com.example.demo.repo.OrderDetailRepo;
import com.example.demo.repo.OrderRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.repo.UserProductRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserProductServiceImpl implements UserProductService{
	
	private final UserProductRepo userProductRepo;
	private final UserRepo userRepo;
	private final ProductRepo productRepo;
//	private String TYPE = "cart";

	@Override
	public UserProduct insertCart(UserProduct cart) {
		UserProduct result = null;
		
		try {
			result = userProductRepo.save(cart);

			log.info("Saving new Cart '{}' to the database", result.getId());
		} catch (Exception e) {
			//log.info("Error while inserting Cart '{}' to the database", result.getId());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public UserProduct updateCart(Long id, UserProduct newCart) {
		UserProduct updatedCart = null;
		try {
			log.info("Update cart '{}' ", newCart.getId());
			updatedCart = userProductRepo.findById(id).map(userProduct -> {
				//userProduct.setModifiedDate(LocalDateTime.now());
				userProduct.setQuantity(userProduct.getQuantity());
				userProduct.setUser(userProduct.getUser());
				userProduct.setType(userProduct.getType());
				return userProductRepo.save(userProduct);
			}).orElseGet(() -> {
				newCart.setId(id);
				return userProductRepo.save(newCart);
			});
		} catch (Exception e) {
			log.info("Failed to update cart '{}' ", newCart.getId());
			e.printStackTrace();
		}

		return updatedCart;
	}

	@Override
	public void deleteCart(Long id) {
		//UserProduct userProduct = getById(id);
		userProductRepo.deleteById(id);
	}

//	@Override
//	public UserProduct getCart(Long id) {
//		
//		return null;
//	}

	@Override
	public UserProduct getCart(String word) {
		
		return null;
	}

//	public List<UserProduct> getCategories() {
//		
//		return null;
//	}
//
//	public List<UserProduct> getCategories(String keyword) {
//		
//		return null;
//	}

	@Override
	public Long count() {
		
		return null;
	}

//	@Override
//	public List<UserProduct> getUserProductByUserIdAndType(Long userId, String type) {
//		List<UserProduct> list = userProductRepo.findAll();
//		return list;
//	}

	@Override
	public void deleteCartByProductId(Long productId) {
		//userProductRepo.deleteByProduct(productId);
	}

//	@Override
//	public List<UserProduct> getUserProductByProductIdAndType(Long productId, String type) {
//		// TODO Auto-generated method stub
////		List<UserProduct> list = userProductRepo.findAllByProductAndType(productId, type);
////		return list;
//		return null;
//	}

	@Override
	public UserProduct getCartByProductId(Long productId) {
		// TODO Auto-generated method stub
		UserProduct userProduct = null;
		return userProduct;
	}

	@Override
	public List<UserProduct> findAllByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProduct getById(Long id) {
		return userProductRepo.findById(id).get();
	}

//	@Override
//	public List<UserProduct> getUserProductByProductIdAndUserIdAndType(Long productId, Long userId, String type) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<UserProduct> getUserProductsByUserIdAndType(Long userId, String type) {
		User user = userRepo.findById(userId).get();
		return userProductRepo.findByUserAndType(user, type);
	}

	@Override
	public List<UserProduct> getUserProductsByProductIdAndType(Long productId, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProduct getUserProductByProductIdAndUserIdAndType(Long productId, Long userId, String type) {
		UserProduct result = null;
		Product product = productRepo.findById(productId).get();
		User user = userRepo.findById(userId).get();
		
		if(product != null && userId != null) {
			try {
				result = userProductRepo.findByUserAndProductAndType(user, product, type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}


}
