package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	private final JavaMailSender javaMailsender;
	
	
	@Autowired
	public EmailService(JavaMailSender javaMailsender) {
		super();
		this.javaMailsender = javaMailsender;
	}



	@Override
	@Async
	public void send(String to, String email) {
		
		try {
			
			MimeMessage mimeMessage = javaMailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Confirm your email");
			helper.setFrom("residenza.elis.bacheca@gmail.com");
			
			javaMailsender.send(mimeMessage);
			
		} catch (MessagingException e) {
			
			LOGGER.error("Failed to send the email", e);
			
			throw new IllegalStateException("Failed to send the email");		
		}
		
	}

}
