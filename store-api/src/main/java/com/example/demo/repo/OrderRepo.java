package com.example.demo.repo;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
	List<Order> findAllByState(int state);

	@Transactional
	@Modifying
	@Query("SELECT e from Order e "	
			+ "where e.shipName LIKE %?1% "
			+ "or e.shipPhone LIKE %?1%"
			+ "or e.shipAddress LIKE %?1% "
			+ "or e.shipDate LIKE %?1% "
			+ "or e.state LIKE %?1% ")
	List<Order> search(String keyword);
	
	List<Order> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	List<Order> findAllByStateOrderByCreatedDateDesc(Integer state);
	
	List<Order> findAllByUserIdAndStateOrderByCreatedDateDesc(Long userId, Integer state);
}
