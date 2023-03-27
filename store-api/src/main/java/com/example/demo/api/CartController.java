package com.example.demo.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProduct;
import com.example.demo.models.ResponseObject;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserProductService;
import com.example.demo.service.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user-products")
@RequiredArgsConstructor
@Slf4j
public class CartController {
	private final UserService userService;
	private final ProductService productService;
	private final UserProductService userProductService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> getCart(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		User user = userService.getUser(id);

		List<UserProduct> result = user.getUserProducts();

		if (result != null) {
			result.removeIf(i -> !i.getType().equals("cart"));

//			carts.removeIf(i -> i.getType().equals("cart"));

			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Carts found!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Carts NOT found!", result));
		}

		return responseEntity;
	}

	@GetMapping(value = "/favorite/{userId}")
	public ResponseEntity<ResponseObject> getFavorites(@PathVariable Long userId) {
		ResponseEntity<ResponseObject> responseEntity = null;
		List<Product> result = null;
		List<UserProduct> userProducts = null;

		try {
			userProducts = userProductService.getUserProductsByUserIdAndType(userId, "like");
			if(userProducts != null && !userProducts.isEmpty()) {
				result = new ArrayList<>();
				
				for(UserProduct us : userProducts) {
					result.add(us.getProduct());
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (result != null) {
				responseEntity = ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("ok", HttpStatus.OK.value(), "Favorite products found!", result));
			} 
//			else {
//				responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//						new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Favorite products NOT found!", result));
//			}
		}

		return responseEntity;
	}

	@GetMapping(value = "/cart/{id}")
	public ResponseEntity<ResponseObject> find(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		// User user = userService.getUser(id);

		UserProduct result = userProductService.getById(id);

		if (result != null) {
			// result.removeIf(i -> !i.getType().equals("cart"));

//			carts.removeIf(i -> i.getType().equals("cart"));

			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Carts found!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Carts NOT found!", result));
		}

		return responseEntity;
	}

	@PostMapping(value = "")
	public ResponseEntity<ResponseObject> addToCart(@RequestBody FormCart formCart) {

		// UserProduct cart =
		// userProductService.getCartByProductId(formCart.getProductId());
		UserProduct cart = userProductService.getUserProductByProductIdAndUserIdAndType(formCart.productId,
				formCart.userId, formCart.type);

		ResponseEntity<ResponseObject> responseEntity = null;
		UserProduct result = null;

		if (cart == null) {
			cart = new UserProduct();
			// cart.setId(new Random().nextLong());
			User user = userService.getUser(formCart.getUserId());
			cart.setUser(user);

			Product product = productService.getProduct(formCart.getProductId());
			cart.setProduct(product);

			cart.setType("cart");
			cart.setQuantity(1);
//			cart.setCreatedDate(LocalDateTime.now());
//			cart.setModifiedDate(LocalDateTime.now());

//			List<UserProduct> userProducts = user.getUserProducts();
//			userProducts.add(cart);
//			
//			user.setUserProducts(userProducts);

			// userService.updateUser(user.getId(), user);

			result = userProductService.insertCart(cart);
		} else {
			Integer oldQuantity = cart.getQuantity();
			Integer newQuantity = oldQuantity + 1;
//			
			//if(newQuantity <= 0) newQuantity = 0;
			cart.setQuantity(newQuantity);

			result = userProductService.updateCart(cart.getId(), cart);
		}

//		Role role = new Role();
//		role.setId(formUser.getRoleId());
//		List<Role> roles = new ArrayList<Role>();
//		roles.add(role);
//		newUser.setRoles(roles);

		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Insert user successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Failed to insert user!", result));
		}

		return responseEntity;

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> updateCart(@PathVariable Long id, @RequestBody FormCart formCart) {
		ResponseEntity<ResponseObject> responseEntity = null;
		try {
			// userProductService.deleteCart(id);

			UserProduct userProduct = userProductService.getById(id);
			userProduct.setQuantity(formCart.getQuantity());
			userProductService.updateCart(id, userProduct);

			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Update Cart successfully!", null));
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Failed to update Cart!", null));
		}

		return responseEntity;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> deleteEmployee(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		try {
			userProductService.deleteCart(id);
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Delete Cart successfully!", null));
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Failed to delete Cart!", null));
		}

		return responseEntity;
	}

	@PutMapping(value = "/favorite")
	public ResponseEntity<ResponseObject> updateFavorite(@RequestBody FormCart formCart) {
		UserProduct userProduct = userProductService.getUserProductByProductIdAndUserIdAndType(formCart.productId,
				formCart.userId, formCart.type);

		ResponseEntity<ResponseObject> responseEntity = null;
		UserProduct result = null;

		if (userProduct == null) {
			userProduct = new UserProduct();
			User user = userService.getUser(formCart.getUserId());
			userProduct.setUser(user);

			Product product = productService.getProduct(formCart.getProductId());
			userProduct.setProduct(product);

			userProduct.setType("like");

			result = userProductService.insertCart(userProduct);
		} else {
			userProductService.deleteCart(userProduct.getId());
		}

		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Add favorite successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(
					new ResponseObject("ok", HttpStatus.ACCEPTED.value(), "Delete favorite successfully!", result));
		}

		return responseEntity;

	}
}

@Data
class FormCart {
	Long userId;
	Long productId;
	Integer quantity;
	String type;
}
