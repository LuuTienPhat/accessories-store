package com.example.demo.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.User;
import com.example.demo.entities.UserProduct;

public interface UserRepo extends JpaRepository<User, Long>{
	User findByUsername(String username);
	
	List<User> findAllByCreatedDateBetween(LocalDateTime createdAtStart, LocalDateTime LocalDate);
}
