package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Mail;
import com.example.demo.models.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mails")
@Slf4j
public class MailController {
	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("storespring21@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	@PostMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> sendMail(@RequestBody Mail mail) {
		ResponseEntity<ResponseObject> responseEntity = null;

		sendSimpleMessage(mail.getEmail(), mail.getSubject(), mail.getBody());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", HttpStatus.OK.value(), "Send successfully!", null));
	}

}
