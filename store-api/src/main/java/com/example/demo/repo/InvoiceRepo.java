package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Invoice;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
//	@Transactional
//	@Modifying
//	@Query("SELECT e from Invoice e WHERE e.name LIKE %?1% OR e.description LIKE %?1%")
//	List<Invoice> search(String keyword);
}
