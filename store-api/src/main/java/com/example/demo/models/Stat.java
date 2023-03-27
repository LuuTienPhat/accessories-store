package com.example.demo.models;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stat {
	private Float growthRateOfCustomers;
	private Float growthRateOfInvoices;
	private Float growthRateOfOrders;
	private Float growthRateOfRevenue;
	private Float growthRateOfCategories;
	private Float growthRateOfTodayRevenueCompareToYesterday;
	
	private Long numberOfCustomers;
	private Long numberOfCategories;
	private Long numberOfOrdersThisMonth;
	private Integer numberOfImportInvoice;
	private Integer numberOfExportInvoice;
	
	
	private Float totalRevenueToday;
	private Float totalRevenueThisMonth;
	
}
