package com.example.demo.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Product;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public String store(MultipartFile file, Object type) {

		String location = null;
		String originalFilename = returnNewOriginalFileName(file);
		
		if (type instanceof Product) {
			Product product = (Product) type;
			location = rootLocation.toAbsolutePath() + File.separator + "products" + File.separator + product.getId();
			createDir(location);
		}

		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = Paths.get(location)
					.resolve(Paths.get(String.valueOf(originalFilename)))
					.normalize().toAbsolutePath();
//			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
//				// This is a security check
//				throw new StorageException(
//						"Cannot store file outside current directory.");
//			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
		
		return originalFilename;
	}
	
	public static Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".")));
	}
	
	private String returnNewOriginalFileName(MultipartFile file) {
		return String.valueOf(System.currentTimeMillis()) + getExtensionByStringHandling(file.getOriginalFilename()).get();
	}

	private Boolean createDir(String dir) {
		Boolean isDirCreated = false;
		if (dir != null && !dir.isEmpty()) {
			Path directory = Paths.get(dir);
			if (!Files.isDirectory(directory)) {
				try {
					Files.createDirectories(directory);
					isDirCreated = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return isDirCreated;
	}
	
//	public static String getImageName(Product product) {
//		
//	}

	public static List<File> getListFiles(String sourcePath, String[] filterFileTypes) {
        List<File> filesInFolder = null;
        try {
            //TODO use filterFileTypes
            filesInFolder = Files.walk(Paths.get(sourcePath), 1).filter(Files::isRegularFile).map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            //_logger.error("Error in getListFiles: " + e.getMessage(), e);
        }
        return filesInFolder;
   }

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			boolean exists = Files.exists(rootLocation, LinkOption.NOFOLLOW_LINKS);
			if (!exists) {
				Files.createDirectories(rootLocation);
			}
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void store(MultipartFile file, String type) {
		String location = null;

		location = rootLocation.toAbsolutePath() + File.separator + type;
//		if(type == "products") {
//			location = rootLocation.toAbsolutePath() + File.separator + "products";
//		}
//		else {
//			
//		}

		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = Paths.get(location).resolve(Paths.get(String.valueOf(System.currentTimeMillis())))
					.normalize().toAbsolutePath();
//			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
//				// This is a security check
//				throw new StorageException(
//						"Cannot store file outside current directory.");
//			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

}