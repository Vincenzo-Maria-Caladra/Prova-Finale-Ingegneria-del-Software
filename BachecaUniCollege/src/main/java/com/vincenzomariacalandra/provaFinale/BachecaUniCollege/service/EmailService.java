package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.EmailSender;

/**
 * @author VectorCode
 *
 */
@Service
public class EmailService implements EmailSender {
	
	// SetUp Logger for logging email sending operation
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	// List all service to use
	private final JavaMailSender javaMailsender;
	
	public EmailService(JavaMailSender javaMailsender) {
		super();
		
		this.javaMailsender = javaMailsender;
	}

	// Send Email
	@Override
	@Async
	public void send(String to, String email) {
		
		if (to == null || email == null || to.isBlank() || email.isBlank()) {
			throw new IllegalArgumentException();
			
		}
		
		try {
			// Util class to setup mail sender
			MimeMessage mimeMessage = javaMailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			// Fill the mail
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Bacheca UniCollege");
			helper.setFrom("residenza.elis.bacheca@gmail.com");
			
			// Send it
			javaMailsender.send(mimeMessage);
			
			
		} catch (Exception e) {
			
			// Else logged the err and then trown a new IllegalStateEx
			LOGGER.error("Failed to send the email", e);
			throw new IllegalStateException("Failed to send the email");		
		}
		
	}

}
