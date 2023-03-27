package com.example.demo.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Feedback;
import com.example.demo.entities.User;
import com.example.demo.repo.FeedbackRepo;
import com.example.demo.service.FeedbackService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
	private final FeedbackRepo feedbackRepo;

	@Override
	public Feedback insertFeedback(Feedback newFeedback) {
		Feedback feedback = null;
		try {
			feedbackRepo.save(newFeedback);
			log.info("Insert feedback {} successfully", newFeedback.getId());
		} catch (Exception e) {
			log.info("Insert feedback {} failed", newFeedback.getId());
			e.printStackTrace();
		}
		return feedback;
	}

	@Override
	public void deleteFeedback(Long id) {
		try {
			feedbackRepo.deleteById(id);
			log.info("Feedback {} is deleted successfully", id);
		} catch (Exception e) {
			log.info("Feedback {} is deleted failed", id);
			e.printStackTrace();
		}
	}

	@Override
	public Feedback getFeedback(Long id) {
		Feedback feedback = null;
		try {
			feedback = feedbackRepo.findById(id).get();
			log.info("Feedback found in database {}", id);
		} catch (Exception e) {
			log.info("Feedback not found in database {}", id);
			e.printStackTrace();
		}
		return feedback;
	}

	@Override
	public Feedback getFeedback(String email) {
		Feedback feedback = null;
		try {
			feedback = feedbackRepo.findByEmail(email);
			log.info("Feedback found in database {}", email);
		} catch (Exception e) {
			log.info("Feedback not found in database {}", email);
			e.printStackTrace();
		}
		return feedback;
	}

	@Override
	public List<Feedback> getFeedbacks() {
		List<Feedback> feedbacks = null;
		try {
			log.info("Fetching all feedbacks");
			feedbacks = feedbackRepo.findAll();
		} catch (Exception e) {
			log.info("Error while fetching data");
			e.printStackTrace();
		}
		return feedbacks;
	}

	@Override
	public Feedback updateFeedback(Long id, Feedback newFeedback) {
		Feedback updatedFeedback = null;
		try {
			log.info("Updating Feedback '{}' to the database", newFeedback.getId());
			updatedFeedback = feedbackRepo.findById(id).map(feedback -> {
				feedback.setApproved(newFeedback.getApproved());
				return feedbackRepo.save(feedback);
			}).orElseGet(() -> {
				newFeedback.setId(id);
				return feedbackRepo.save(newFeedback);
			});
		} catch (Exception e) {
			log.info("Error while updating Feedback '{}' to the database", newFeedback.getId());
			e.printStackTrace();
		}

		return updatedFeedback;
	}

	@Override
	public Feedback getFeedback(User user) {
		Feedback feedback = null;
		try {
			feedback = feedbackRepo.findByUser(user);
			log.info("Feedback found in database {}", user.getId());
		} catch (Exception e) {
			log.info("Feedback not found in database {}", user.getId());
			e.printStackTrace();
		}
		return feedback;
	}

	@Override
	public List<Feedback> getFeedbacks(int from, int limit) {
		Page<Feedback> page = feedbackRepo.findAll(PageRequest.of(from, limit));
		return page.toList();
	}
}
