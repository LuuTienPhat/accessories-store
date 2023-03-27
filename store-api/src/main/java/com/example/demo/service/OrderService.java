package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entities.Order;

public interface OrderService {
	Order insertOrder(Order order);

	Order updateOrder(Long id, Order newOrder);

	void deleteOrder(Long id);

	Order getOrder(Long id);
	
	List<Order> getOrders();
	
	List<Order> getOrdersByUserId(Long userId);
	
	List<Order> getOrders(int offset, int limit);
	
	List<Order> getOrders(String keyword);
	
	List<Order> getOrders(int state);
	
	List<Order> getOrders(LocalDateTime startDate, LocalDateTime endDate, int state);
	
	List<Order> getOrdersOrderByCreatedDateDesc(int state);
	
	Long count();
	
	Long countByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
