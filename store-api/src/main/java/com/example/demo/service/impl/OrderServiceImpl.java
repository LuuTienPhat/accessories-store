package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderDetail;
import com.example.demo.entities.Product;
import com.example.demo.repo.OrderDetailRepo;
import com.example.demo.repo.OrderRepo;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderRepo orderRepo;
	private final OrderDetailRepo orderDetailRepo;
	private final ProductService productService;

	@Override
	public Order insertOrder(Order order) {
		Order result = null;
		try {
			result = orderRepo.save(order);

			for (OrderDetail orderDetail : order.getOrderDetails()) {
				orderDetailRepo.save(orderDetail);
			}

			log.info("Saving new Order '{}' to the database", order.getId());
		} catch (Exception e) {
			log.info("Error while inserting Order '{}' to the database", order.getId());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Order updateOrder(Long id, Order newOrder) {
		Order updatedOrder = null;
		try {
			log.info("Updating Order '{}' to the database", newOrder.getId());
			updatedOrder = orderRepo.findById(id).map(order -> {
//				order.setOrderDate(newOrder.getOrderDate());
//				order.setShippedDate(newOrder.getShippedDate());
//				order.setOrderDetails(newOrder.getOrderDetails());
//				order.setShipAddress(newOrder.getShipAddress());
//				order.setShipNote(newOrder.getShipNote());
//				order.setShipPhone(newOrder.getShipPhone());
				if (order.getState() != 2) {
					order.setState(newOrder.getState());

					if (newOrder.getState() == 2) {
						for (OrderDetail orderDetail : order.getOrderDetails()) {
							Product product = productService.getProduct(orderDetail.getProduct().getId());
							int oldQuant = product.getQuantity();
							int newQuant = oldQuant - orderDetail.getQuantity();
							product.setQuantity(newQuant);
							productService.updateProduct(product.getId(), product);
						}
					}
				}
				return orderRepo.save(order);
			}).orElseGet(() -> {
				newOrder.setId(id);
				return orderRepo.save(newOrder);
			});
		} catch (Exception e) {
			log.info("Error while updating Order '{}' to the database", newOrder.getId());
			e.printStackTrace();
		}

		return updatedOrder;
	}

	@Override
	public void deleteOrder(Long id) {
		orderRepo.deleteById(id);
	}

	@Override
	public Order getOrder(Long id) {
		Order order = null;
		try {
			order = orderRepo.findById(id).get();
			log.info("Order found '{}'", order.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

//	@Override
//	public Order getOrder(String word) {
//		Order Order = null;
//		try {
//			Order = orderRepo.findByWord(word);
//			log.info("Order found '{}'", Order.getId());
//		} catch (Exception e) {
//			log.info("Order not found '{}'", Order.getId());
//			e.printStackTrace();
//		}
//		return Order;
//	}

	@Override
	public List<Order> getOrders() {
		List<Order> orders = null;
		try {
			log.info("Fetching all Orders");
			orders = orderRepo.findAll();
		} catch (Exception e) {
			log.info("Error while fetching Orders");
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public List<Order> getOrders(int offset, int limit) {
		Page<Order> page = orderRepo.findAll(PageRequest.of(offset, limit));
		return page.toList();
	}

//	@Override
//	public List<Order> getOrders(String keyword) {
//		return orderRepo.search(keyword);
//	}

	@Override
	public Long count() {
		return orderRepo.count();
	}

	@Override
	public List<Order> getOrders(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrders(int state) {
		// TODO Auto-generated method stub
		return orderRepo.findAllByState(state);
	}

	@Override
	public List<Order> getOrdersOrderByCreatedDateDesc(int state) {
		return orderRepo.findAllByStateOrderByCreatedDateDesc(state);
	}

	@Override
	public Long countByCreatedDateBetween(LocalDateTime orderDateStart, LocalDateTime orderDateEnd) {
		List<Order> ordersThisMonth = orderRepo.findAllByCreatedDateBetween(orderDateStart, orderDateEnd);
		return Long.valueOf(ordersThisMonth.size());
	}

	@Override
	public List<Order> getOrders(LocalDateTime orderDateStart, LocalDateTime orderDateEnd, int state) {
		List<Order> orders = orderRepo.findAllByCreatedDateBetween(orderDateStart, orderDateEnd);

		orders.removeIf(item -> item.getState() != state);
		return orders;
	}

	@Override
	public List<Order> getOrdersByUserId(Long userId) {
		List<Order> orders = orderRepo.findAllByUserIdAndStateOrderByCreatedDateDesc(userId, 3);

		orders.removeIf(item -> item.getState() != 3);
		return orders;
	}

}
