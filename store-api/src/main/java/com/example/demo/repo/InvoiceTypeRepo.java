package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Category;
import com.example.demo.entities.InvoiceDetail;
import com.example.demo.entities.InvoiceType;

public interface InvoiceTypeRepo extends JpaRepository<InvoiceType, Long> {

}
