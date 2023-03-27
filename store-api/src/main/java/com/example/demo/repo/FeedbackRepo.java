package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Feedback;
import com.example.demo.entities.User;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
	Feedback findByUser(User user);
	Feedback findByEmail(String email);
}
