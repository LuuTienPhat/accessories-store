package com.example.demo.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Category;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderDetail;
import com.example.demo.entities.Product;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.models.OrderStatistics;
import com.example.demo.models.Revenue;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.OrderRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.StatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StatServiceImpl implements StatService {

	private final UserRepo userRepo;
	private final RoleRepo roleRepo;
	private final OrderRepo orderRepo;
	private final CategoryRepo categoryRepo;
	private final ProductRepo productRepo;

	// GET ORDER STATISTICS
	@Override
	public List<OrderStatistics> getOrderStatistics() {
		List<OrderStatistics> orderStatisticsByMonths = new ArrayList<OrderStatistics>();

		for (int i = 0; i < 6; i++) {
			LocalDate now = LocalDate.now().minusMonths(i);
			LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
			LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());

			List<Order> orders = orderRepo.findAllByCreatedDateBetween(firstDayOfMonth.atStartOfDay(),
					lastDayOfMonth.atStartOfDay());

			orders.removeIf(item -> item.getState() != 2);

			OrderStatistics orderStatistics = new OrderStatistics();
			orderStatistics.setDate(java.sql.Date.valueOf(now));
			orderStatistics.setNumber(orders.size());
			orderStatisticsByMonths.add(orderStatistics);
		}

		return orderStatisticsByMonths;
	}

	// GET MONTHLY REVENUE
	@Override
	public List<Revenue> getMonthlyRevenue(int numberOfMonths) {
		List<Revenue> montlyRevenue = new ArrayList<Revenue>();

		for (int i = 0; i < numberOfMonths; i++) {
			LocalDate now = LocalDate.now().minusMonths(i);
			LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
			LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());

			List<Order> ordersLastMonth = orderRepo.findAllByCreatedDateBetween(firstDayOfMonth.atStartOfDay(),
					lastDayOfMonth.atStartOfDay());

			ordersLastMonth.removeIf(item -> item.getState() != 2);

			float money = 0;
			for (Order order : ordersLastMonth) {
				money += Order.getTotalPrice(order);
			}

			Revenue revenue = new Revenue(java.sql.Date.valueOf(now), money);
			montlyRevenue.add(0, revenue);
		}

		return montlyRevenue;
	}

	// GET GROWTH PERCENTAGE OF USERS BY MONTH
	@Override
	public float getMonthOverMonthGrowthRateOfCustomers() {
		LocalDate now = LocalDate.now();

		Role customerRole = roleRepo.findByName("ROLE_CUSTOMER");

		List<User> customersRegiteredThisMonth = userRepo.findAllByCreatedDateBetween(
				now.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(), 
				now.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));
		List<User> customersRegiteredLastMonth = userRepo.findAllByCreatedDateBetween(
				now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));

		customersRegiteredLastMonth.removeIf(item -> !item.getRoles().contains(customerRole));
		customersRegiteredThisMonth.removeIf(item -> !item.getRoles().contains(customerRole));

		float numberOfCustomersRegisteredThisMonth = customersRegiteredThisMonth.size();
		float numberOfCustomersRegisteredLastMonth = customersRegiteredLastMonth.size();
		if (numberOfCustomersRegisteredThisMonth == numberOfCustomersRegisteredLastMonth)
			return 0;
		else if (numberOfCustomersRegisteredLastMonth == 0) {
			return ((numberOfCustomersRegisteredLastMonth + numberOfCustomersRegisteredThisMonth)
					/ numberOfCustomersRegisteredThisMonth) * 100;
		} else
			return ((numberOfCustomersRegisteredThisMonth - numberOfCustomersRegisteredLastMonth)
					/ numberOfCustomersRegisteredLastMonth) * 100;
	}

	// GET GROWTH PERCENTAGE OF ORDERS
	@Override
	public float getMonthOverMonthGrowthRateOfOrders() {
		LocalDate now = LocalDate.now();

		List<Order> ordersThisMonth = orderRepo.findAllByCreatedDateBetween(
				now.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));
		List<Order> ordersLastMonth = orderRepo.findAllByCreatedDateBetween(
				now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));

		float numberOfOrdersThisMonth = ordersThisMonth.size();
		float numberOfOrdersLastMonth = ordersLastMonth.size();

		if (numberOfOrdersThisMonth == numberOfOrdersLastMonth)
			return 0;
		else if (numberOfOrdersLastMonth == 0) {
			return ((numberOfOrdersLastMonth + numberOfOrdersThisMonth) / numberOfOrdersThisMonth) * 100;
		} else
			return ((numberOfOrdersThisMonth - numberOfOrdersLastMonth) / numberOfOrdersLastMonth) * 100;
	}

	public float getTotalRevenue(List<Order> list) {
		float revenue = 0;
		for (Order order : list) {
			revenue += getTotalPrice(order);
		}
		return revenue;
	}

	// GET TOTAL PRICE OF ORDER
	public float getTotalPrice(Order order) {
		float totalPrice = 0;
		for (OrderDetail orderDetail : order.getOrderDetails()) {
			totalPrice += (orderDetail.getProduct().getPrice() * orderDetail.getQuantity());
		}
		return totalPrice;
	}

	// GET MONTH OVER MONTH REVENUE
	@Override
	public float getMonthOverMonthGrowthRateOfRevenue() {
		LocalDate now = LocalDate.now();

		List<Order> ordersThisMonth = orderRepo.findAllByCreatedDateBetween(
				now.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));
		List<Order> ordersLastMonth = orderRepo.findAllByCreatedDateBetween(
				now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));

		ordersThisMonth.removeIf(item -> item.getState() != 2);
		ordersLastMonth.removeIf(item -> item.getState() != 2);

		float revenueThisMonth = getTotalRevenue(ordersThisMonth);
		float revenueLastMonth = getTotalRevenue(ordersLastMonth);

		if (revenueThisMonth == revenueLastMonth)
			return 0;
		else if (revenueLastMonth == 0) {
			return ((revenueLastMonth + revenueThisMonth) / revenueThisMonth) * 100;
		} else
			return ((revenueThisMonth - revenueLastMonth) / revenueLastMonth) * 100;
	}

	// GET GROWTH PERCENTAGE OF CATEGORIES
	@Override
	public float getMonthOverMonthGrowthRateOfCategories() {
		LocalDate now = LocalDate.now();

		List<Category> thisMonthCategories = categoryRepo.findAllByCreatedDateBetween(
				now.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(), 
				now.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));
		List<Category> lastMonthCategories = categoryRepo.findAllByCreatedDateBetween(
				now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));

		float numberOfCategoriesThisMonth = thisMonthCategories.size();
		float numberOfCategoriesLastMonth = lastMonthCategories.size();
		if (numberOfCategoriesThisMonth == numberOfCategoriesLastMonth)
			return 0;
		else if (numberOfCategoriesLastMonth == 0) {
			return ((numberOfCategoriesLastMonth + numberOfCategoriesThisMonth) / numberOfCategoriesThisMonth) * 100;
		} else
			return ((numberOfCategoriesThisMonth - numberOfCategoriesLastMonth) / numberOfCategoriesLastMonth) * 100;
	}

	// GET GROWTH PERCENTAGE OF PRODUCTS
	@Override
	public float getMonthOverMonthGrowthRateOfProducts() {
		LocalDate now = LocalDate.now();

		List<Product> thisMonthProducts = productRepo.findAllByCreatedDateBetween(
				now.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(), 
				now.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));
		List<Product> lastMonthProducts = productRepo.findAllByCreatedDateBetween(
				now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));

		float numberOfProductsThisMonth = thisMonthProducts.size();
		float numberOfProductsLastMonth = lastMonthProducts.size();
		if (numberOfProductsThisMonth == numberOfProductsLastMonth)
			return 0;
		else if (numberOfProductsLastMonth == 0) {
			return ((numberOfProductsLastMonth + numberOfProductsThisMonth) / numberOfProductsThisMonth) * 100;
		} else
			return ((numberOfProductsThisMonth - numberOfProductsLastMonth) / numberOfProductsLastMonth) * 100;
	}

	// GET GROWTH PERCENTAGE OF TODAY REVENUE COMPARE TO YESTERDAY
	public float getDayOverDayGrowthRateOfOrders() {

		LocalDate now = LocalDate.now();

		List<Order> todayOrders = orderRepo.findAllByCreatedDateBetween(
				now.atStartOfDay(),
				now.atTime(LocalTime.MAX));
		List<Order> yesterdayOrders = orderRepo.findAllByCreatedDateBetween(
				now.minusDays(1).atStartOfDay(),
				now.minusDays(1).atTime(LocalTime.MAX));

		todayOrders.removeIf(item -> item.getState() != 2);
		yesterdayOrders.removeIf(item -> item.getState() != 2);

		float revenueToday = getTotalRevenue(todayOrders);
		float revenueYesterDay = getTotalRevenue(yesterdayOrders);
		if (revenueToday == revenueYesterDay)
			return 0;
		else if (revenueYesterDay == 0) {
			return ((revenueYesterDay + revenueToday) / revenueToday) * 100;
		} else
			return ((revenueToday - revenueYesterDay) / revenueYesterDay) * 100;
	}

	// GET TOTAL REVENUE TODAY
	public float getTotalRevenueToday() {

		LocalDate now = LocalDate.now();

		List<Order> todayOrders = orderRepo.findAllByCreatedDateBetween(
				now.atStartOfDay(),
				now.atTime(LocalTime.MAX));

		todayOrders.removeIf(item -> item.getState() != 2);

		return getTotalRevenue(todayOrders);
	}

	// GET TOTAL REVENUE THIS MONTH
	public float getTotalRevenueThisMonth() {
		LocalDate now = LocalDate.now();
		List<Order> ordersThisMonth = orderRepo.findAllByCreatedDateBetween(
				now.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
				now.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay());

		ordersThisMonth.removeIf(item -> item.getState() != 2);
		return getTotalRevenue(ordersThisMonth);
	}

//	// GET MOST VIEWED PRODUCTS
//		public List<Product> getMostViewedProducts() throws IOException {
//			Session session = factory.openSession();
//			String hql = "FROM ProductEntity p ORDER BY p.views DESC ";
//			Query query = session.createQuery(hql);
//			List<ProductEntity> list = query.list();
//			Products products = new Products((ArrayList<ProductEntity>) list);
//			session.close();
//			return products;
//		}
}
