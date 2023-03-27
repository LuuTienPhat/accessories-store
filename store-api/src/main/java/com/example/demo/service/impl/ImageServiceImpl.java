package com.example.demo.service.impl;

import java.util.List;

import org.hibernate.type.ImageType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.constants.ImageStatus;
import com.example.demo.entities.Category;
import com.example.demo.entities.Image;
import com.example.demo.entities.Product;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.ImageRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageServiceImpl implements ImageService {
	private final ImageRepo imageRepo;
	private final ProductRepo productRepo;
	
	@Override
	public Image insertImage(Image image) {
		Image result = null;
		try {
			log.info("Saving new image '{}' to the database", image.getPath());
			result = imageRepo.save(image);
		} catch (Exception e) {
			log.info("Error while inserting image '{}' to the database",  image.getPath());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Image updateImage(Long id, Image newImage) {
		Image updatedImage = null;
		try {
			log.info("Updating image '{}' to the database", newImage.getId());
			updatedImage = imageRepo.findById(id).map(image -> {
				image.setStatus(newImage.getStatus());
				image.setPath(newImage.getPath());
				
				return imageRepo.save(image);
			}).orElseGet(() -> {
				newImage.setId(id);
				return imageRepo.save(newImage);
			});
		} catch (Exception e) {
			log.info("Error while updating category '{}' to the database", newImage.getId());
			e.printStackTrace();
		}

		return updatedImage;
	}

	@Override
	public void deleteImage(Long id) {
		Image image = getImage(id);
		if(image != null) {
			image.setStatus(ImageStatus.VOID);
			updateImage(id, image);
		}
	}

	@Override
	public Image getImage(Long id) {
		Image image = null;
		try {
			image = imageRepo.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public List<Image> getImagesByProductId(Long productId) {
		Product p = productRepo.findById(productId).get();
		List<Image> images = null;
		if(p != null) {
			images = imageRepo.findByProduct(p);
		}
		return images;
	}

//	@Override
//	public List<Image> getImagesByCategoryId(String categoryId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
