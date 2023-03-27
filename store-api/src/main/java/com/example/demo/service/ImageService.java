package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Image;

public interface ImageService {
	Image insertImage(Image image);

	Image updateImage(Long id, Image newImage);

	void deleteImage(Long id);

	Image getImage(Long id);

	//Image getImage(String word);
	
	List<Image> getImagesByProductId(Long productId);
	
	//List<Image> getImagesByCategoryId(String categoryId);
	
	//List<Image> getCategories(String keyword);
	
	//Long count();
}
