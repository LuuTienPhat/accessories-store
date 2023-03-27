package com.example.demo.models;

import com.example.demo.entities.Product;

import lombok.Data;

@Data
public class MostFavoriteProduct {
	private Product product;
	private Integer count;
}
