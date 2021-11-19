package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import static org.mockito.Mockito.lenient;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ConfirmationTokenRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ConfirmationTokenService;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author CalandraVM
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ConfirmationTokenServiceImpUnitTest {
	
	@Mock
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	private ConfirmationTokenService service;
	 
	@BeforeEach
	public void setUp() {
		
		service = new ConfirmationTokenService(confirmationTokenRepository);
		
	}
	
	@Test
	public void saveConfirmationTokenTest() {
		ConfirmationToken token = new ConfirmationToken();
		
		assertEquals("Token could not be null!", service.saveConfirmationToken(null));
				
		lenient().when(confirmationTokenRepository.save(token)).thenReturn(null);
		assertEquals("Saved token is null", service.saveConfirmationToken(token));
		
		lenient().when(confirmationTokenRepository.save(token)).thenReturn(token);
		assertNull(service.saveConfirmationToken(token));
		
	}
	
	@Test
	public void getTokenTest() {
		
		String token = "token";
		
		assertEquals(Optional.ofNullable(null), service.getToken(null));
		
		lenient().when(confirmationTokenRepository.findByToken(token)).thenReturn(Optional.of(new ConfirmationToken()));
		assertEquals(Optional.of(new ConfirmationToken()), service.getToken(token));
	}
	
	@Test 
	public void setConfirmedAtTest() {
		
		String token = "token";
		
		assertEquals("Token could not be null or empty", service.setConfirmedAt(null));
		
		assertNull(service.setConfirmedAt(token));
		
	}
}
