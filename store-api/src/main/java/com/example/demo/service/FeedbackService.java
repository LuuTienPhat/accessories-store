package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Feedback;
import com.example.demo.entities.User;


public interface FeedbackService {
	Feedback insertFeedback(Feedback Feedback);

	Feedback updateFeedback(Long id, Feedback newFeedback);

	void deleteFeedback(Long id);

	Feedback getFeedback(Long id);

	Feedback getFeedback(String email);

	Feedback getFeedback(User user);

	List<Feedback> getFeedbacks();
	
	List<Feedback> getFeedbacks(int from, int limit);
}
