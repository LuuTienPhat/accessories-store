package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProduct;

public interface UserProductRepo extends JpaRepository<UserProduct, Long>{
//	@Transactional
//	@Modifying
//	@Query("SELECT e from Cart e WHERE e.name LIKE %?1% OR e.description LIKE %?1%")
//	List<Category> search(String keyword);
	
	List<UserProduct> findByUser(User user);
	
	List<UserProduct> findByProduct(Product product);
	
	List<UserProduct> findByUserAndType(User user, String type);
	
	List<UserProduct> findByProductAndType(Product product, String type);
	
	UserProduct findByUserAndProductAndType(User user, Product product, String type);
	
	List<UserProduct> findAllByType(String type);

	@Query("SELECT up, count(up.product) from UserProduct up WHERE up.type =  ?1 GROUP BY up.product order by count(up.product) desc")
	List<UserProduct> findAllByTypeOrderByProductDesc(String type);
	
	void deleteByProduct(Long productId);
}
