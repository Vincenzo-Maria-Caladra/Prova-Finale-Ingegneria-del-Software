package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.EmailService;



/**
 * @author CalandraVM
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class EmailServiceImpUnitTest {
	
	@Mock
	private JavaMailSender javaMailsender;
	
	private EmailService service;
	
	@BeforeEach
	public void setUp() {
		
		service = new EmailService(javaMailsender);
	}
	
	@Test
	public void sendTest () {
		
		assertThrows(IllegalArgumentException.class, () -> service.send(null, null));

		assertThrows(IllegalArgumentException.class, () -> service.send("", ""));
	
		assertThrows(IllegalStateException.class, () -> {
			service.send("prova@mail.com","email");
		});
		
	}
	
}
