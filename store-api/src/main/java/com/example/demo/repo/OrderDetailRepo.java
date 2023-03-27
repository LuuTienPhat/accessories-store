package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.OrderDetail;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
//	List<Order> findAllByState(int state);
//
//	@Transactional
//	@Modifying
//	@Query("SELECT e from Order e "
//			+ "WHERE e.orderDate LIKE %?1% "
//			+ "or e.shippedDate LIKE %?1% "
//			+ "or e.shipName LIKE %?1% "
//			+ "or e.state LIKE %?1% "
//			+ "or e.shipAddress LIKE %?1% "
//			+ "or e.shipPhone LIKE %?1%")
//	List<Order> search(String keyword);
//	
//	List<Order> findAllByOrderDateBetween(LocalDateTime orderDateStart, LocalDateTime orderDateEnd);
//	
//	List<Order> findAllByStateOrderByOrderDateAsc(Integer state);
}
