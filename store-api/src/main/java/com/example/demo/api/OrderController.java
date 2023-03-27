package com.example.demo.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderDetail;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProduct;
import com.example.demo.models.ResponseObject;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserProductService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final OrderService orderService;
	private final UserProductService userProductService;

	@GetMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> allButLimit(@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "search", required = false) String keyword,
			@RequestParam(value = "state", required = false) Integer state, 
			@RequestParam(value = "userId", required = false) Long userId) {
		ResponseEntity<ResponseObject> responseEntity = null;

		List<Order> result = null;

		if (keyword != null) {
			result = orderService.getOrders(keyword);
		} else if (state != null) {
			result = orderService.getOrders(state);
		}
		else if(userId != null) {
			result = orderService.getOrdersByUserId(userId);
		}
		else {
			result = orderService.getOrders();
		}
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch Orders successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch Orders!", result));
		}

		return responseEntity;
	}

	@GetMapping(value = "/latest")
	@ResponseBody
	public ResponseEntity<ResponseObject> latest() {
		ResponseEntity<ResponseObject> responseEntity = null;

		List<Order> result = null;

		result = orderService.getOrdersOrderByCreatedDateDesc(0);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch Orders successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch Orders!", result));
		}

		return responseEntity;
	}

	@PostMapping(value = "")
	public ResponseEntity<ResponseObject> newOrder(@RequestBody FormOrder formOrder) {

		ResponseEntity<ResponseObject> responseEntity = null;

		Order newOrder = new Order();
		newOrder.setShipAddress(formOrder.getShipAddress());
		newOrder.setShipPhone(formOrder.getShipPhone());
		newOrder.setShipNote(formOrder.getShipNote());
		newOrder.setShipName(formOrder.getShipName());
		newOrder.setState(formOrder.getState());

		User user = new User();
		user.setId(formOrder.getUserId());
		newOrder.setUser(user);

		newOrder.setCreatedDate(LocalDateTime.now());

		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		for (FormOrderDetail formOrderDetail : formOrder.getOrderDetails()) {
			OrderDetail orderDetail = new OrderDetail();
			Product product = new Product();
			product.setId(formOrderDetail.getProductId());
			orderDetail.setProduct(product);
			orderDetail.setQuantity(formOrderDetail.getQuantity());
			orderDetail.setOrder(newOrder);
			orderDetails.add(orderDetail);
			
			UserProduct userProduct = userProductService.getById(formOrderDetail.getCartId());
			userProductService.deleteCart(userProduct.getId());
		}
		newOrder.setOrderDetails(orderDetails);

		Order result = orderService.insertOrder(newOrder);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Insert Order successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to insert Order!", result));
		}

		return responseEntity;

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> one(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		Order result = orderService.getOrder(id);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Order found!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Order NOT found!", result));
		}

		return responseEntity;
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> replaceOrder(@RequestBody FormOrder formOrder, @PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;

		Order newOrder = new Order();
		newOrder.setState(formOrder.getState());
		newOrder.setId(id);

		Order result = orderService.updateOrder(id, newOrder);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Update Order successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to update Order!", result));
		}

		return responseEntity;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> deleteEmployee(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		try {
			orderService.deleteOrder(id);
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Delete Order successfully!", null));
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Failed to delete Order!", null));
		}

		return responseEntity;
	}

	@GetMapping(value = "/count")
	@ResponseBody
	ResponseEntity<ResponseObject> count() {
		Long result = orderService.count();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", HttpStatus.OK.value(), "Order count", result));

	}
}

@Data
class FormOrder {
	Long userId;
	String shipPhone;
	String shipAddress;
	String shipName;
	String shipNote;
	Integer state;
	List<FormOrderDetail> orderDetails;
}

@Data
class FormOrderDetail {
	Long cartId;
	Long productId;
	Integer quantity;
}
