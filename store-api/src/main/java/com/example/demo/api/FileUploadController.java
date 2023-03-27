package com.example.demo.api;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.constants.ImageStatus;
import com.example.demo.entities.Category;
import com.example.demo.entities.Image;
import com.example.demo.entities.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ImageService;
import com.example.demo.service.ProductService;
import com.example.demo.storage.StorageFileNotFoundException;
import com.example.demo.storage.StorageService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/files")
@Controller
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

	private final StorageService storageService;
	private final CategoryService categoryService;
	private final ProductService productService;
	private final ImageService imageService;

//	@Autowired
//	public FileUploadController(StorageService storageService, CategoryService categoryService, ProductService productService, ImageService imageService) {
//		this.storageService = storageService;
//	}

	@GetMapping("")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("download/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@GetMapping("{filename:.+}")
	@ResponseBody
	public ResponseEntity<byte[]> showFile(@PathVariable String filename) throws IOException {

		Resource file = storageService.loadAsResource(filename);
		byte[] bytesArray = file.getInputStream().readAllBytes();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytesArray);
	}
	
	@GetMapping("products/{id}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<byte[]> getProductFile(@PathVariable String filename, @PathVariable String id) throws IOException {

		filename = "products/" + id + "/" + filename;
		Resource file = storageService.loadAsResource(filename);
		byte[] bytesArray = file.getInputStream().readAllBytes();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytesArray);
	}

	@PostMapping("")
	public String handleFileUpload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("action") String action,
			@RequestParam(value = "productId",required = false) Long productId,
			@RequestParam(value = "categoryId" ,required = false) Long categoryId,
			RedirectAttributes redirectAttributes) {

		Category category = null;
		Product product = null;
		String originalFilename = null;
		
		if(categoryId != null && productId == null) {
			storageService.store(file, "categories");
			category = categoryService.getCategory(categoryId);
		}
		else {
			product = productService.getProduct(productId);
			originalFilename = storageService.store(file, product);
		}
		
		if(action.equalsIgnoreCase("edit")) {
			
			List<Image> images = imageService.getImagesByProductId(productId);
			if(images != null) {
				for (Iterator iterator = images.iterator(); iterator.hasNext();) {
					Image image = (Image) iterator.next();
					imageService.deleteImage(image.getId());
				}
			}	
		}
		
		Image image = new Image();
		image.setPath(originalFilename);
		image.setCategory(category);
		image.setProduct(product);
		image.setStatus("NEW");
		
		imageService.insertImage(image);
		
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/files";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	@Data
	class FormImage {
		String productId;
		String categoryId;
	}
}