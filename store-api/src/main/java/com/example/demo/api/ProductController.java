package com.example.demo.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProduct;
import com.example.demo.models.ResponseObject;
import com.example.demo.repo.UserProductRepo;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserProductService;
import com.example.demo.service.UserService;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	private final ProductService productService;
	private final CategoryService categoryService;
	private final UserService userService;
	private final UserProductService userProductService;
	private final UserProductRepo userProductRepo;

	@GetMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> allButLimit(@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "search", required = false) String keyword,
			@RequestParam(value = "categoryId", required = false) Long categoryId) {

		ResponseEntity<ResponseObject> responseEntity = null;
		List<Product> result = null;
		if(categoryId != null) {
			result = productService.getProductsByCategoryId(categoryId);
		}
		else if (keyword != null) {
			result = productService.getProducts(keyword);
		} else if (offset != null && limit != null){
			result = productService.getProducts(offset, limit);
		}
		else {
			result = productService.getProducts();
		}
		if (result != null) {
			
			result.removeIf(item -> item.getStatus().equalsIgnoreCase("VOID"));
			
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Product product = (Product) iterator.next();
				
				product.getImages().removeIf(item -> item.getStatus().equalsIgnoreCase("VOID"));
				
			}
			
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch Products successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch Products!", result));
		}

		return responseEntity;
	}

	@PostMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> newProduct(@RequestBody FormProduct formProduct) {
		ResponseEntity<ResponseObject> responseEntity = null;
		
		Product newProduct = new Product();
		newProduct.setName(formProduct.getName());
		newProduct.setDescription(formProduct.getDescription());
		newProduct.setUnit(formProduct.getUnit());
		newProduct.setPrice(formProduct.getPrice());
		newProduct.setStatus("NEW");
		
		Category category = categoryService.getCategory(formProduct.getCategoryId());
		//category.setId(formProduct.getCategoryId());
		newProduct.setCategory(category);
		
		Product result = productService.insertProduct(newProduct);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Insert Product successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to insert Product!", result));
		}
		return responseEntity;
	}

	@GetMapping(value = "/{id}")
	@ResponseBody
	ResponseEntity<ResponseObject> one(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		Product result = productService.getProduct(id);
		
		if (result != null) {
			result.getImages().removeIf(item -> item.getStatus().equalsIgnoreCase("VOID"));
			
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Product found!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Product NOT found!", result));
		}
		return responseEntity;
	}

	@PutMapping(value = "/{id}")
	@ResponseBody
	ResponseEntity<ResponseObject> replaceProduct(@RequestBody FormProduct formProduct, @PathVariable Long id) {
		
		Product newProduct = productService.getProduct(id);
		newProduct.setName(formProduct.getName());
		newProduct.setDescription(formProduct.getDescription());
		newProduct.setUnit(formProduct.getUnit());
		newProduct.setPrice(formProduct.getPrice());
		newProduct.setStatus("NEW");
		
		//Category category = categoryService.getCategory(id);
		//category.setId(formProduct.getCategoryId());
		//newProduct.setCategory(category);
		ResponseEntity<ResponseObject> responseEntity = null;
		
		Product result = productService.updateProduct(id, newProduct);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Update Product successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to update Product!", result));
		}
		return responseEntity;
	}

	@DeleteMapping(value = "/{id}")
	@ResponseBody
	ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		try {
			//productService.deleteProduct(id);
			Product newProduct = productService.getProduct(id);
			newProduct.setStatus("VOID");
			
			productService.updateProduct(id, newProduct);
			
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Delete Product successfully!", null));
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to delete Product!", null));
		}

		return responseEntity;
	}

	@GetMapping(value = "/count")
	@ResponseBody
	ResponseEntity<ResponseObject> count() {
		Long result = productService.count();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", HttpStatus.OK.value(), "Product count", result));

	}

	@GetMapping(value = "/most-viewed")
	@ResponseBody
	ResponseEntity<ResponseObject> getMostViewedProducts() {
		ResponseEntity<ResponseObject> responseEntity = null;
		List<Product> result = null;
		result = productService.getTop5ProductsByViews();
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch Products successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch Products!", result));
		}

		return responseEntity;

	}
	
	@GetMapping(value = "/most-favorite")
	@ResponseBody
	ResponseEntity<ResponseObject> getMostFavoriteProducts() {
		ResponseEntity<ResponseObject> responseEntity = null;
		
		List<UserProduct> userProducts = null;
		userProducts = userProductRepo.findAllByTypeOrderByProductDesc("like");
		
		List<Product> result = new ArrayList<>();
		userProducts.forEach(t -> result.add(t.getProduct()));
//		
//		System.out.print(result1);
		
//		List<Product> result = null;
//		result = productService.getProducts();
		
		//List<User> users = userService.getUsers();
		
//		Collections.sort(result, (o1, o2) -> {
//			return o2.getUserProducts().size() - o1.getUserProducts().size();
//		});
		
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch Products successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch Products!", result));
		}

		return responseEntity;

	}
	
	@GetMapping(value = "/up/{id}")
	@ResponseBody
	ResponseEntity<ResponseObject> increase(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		Product result = productService.getProduct(id);
		
		if (result != null) {
			//result.getImages().removeIf(item -> item.getStatus().equalsIgnoreCase("VOID"));
			Integer views = result.getViews();
			result.setViews(views + 1);
			
			productService.updateProduct(id, result);
			
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Product found!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Product NOT found!", result));
		}
		return responseEntity;
	}
}

@Data
class FormProduct {
	Long id;
	Long categoryId;
	Float price;
	String name;
	String description;
	String unit;
}
