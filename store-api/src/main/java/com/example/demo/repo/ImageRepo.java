package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Image;
import com.example.demo.entities.Product;

public interface ImageRepo extends JpaRepository<Image, Long> {
	List<Image> findByProduct(Product product);
}