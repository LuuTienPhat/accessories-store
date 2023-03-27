package com.example.demo.service;

import java.util.List;

import com.example.demo.models.OrderStatistics;
import com.example.demo.models.Revenue;

public interface StatService {
	float getMonthOverMonthGrowthRateOfCustomers();
	
	float getMonthOverMonthGrowthRateOfOrders();
	
	float getMonthOverMonthGrowthRateOfProducts();
	
	float getMonthOverMonthGrowthRateOfCategories();
	
	float getMonthOverMonthGrowthRateOfRevenue();
	
	float getDayOverDayGrowthRateOfOrders();
	
	float getTotalRevenueToday();
	
	float getTotalRevenueThisMonth();

	List<Revenue> getMonthlyRevenue(int numberOfMonths);
	
	List<OrderStatistics> getOrderStatistics();
}
