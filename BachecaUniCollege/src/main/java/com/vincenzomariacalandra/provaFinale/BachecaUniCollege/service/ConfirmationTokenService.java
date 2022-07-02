package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ConfirmationTokenRepository;

/**
 * @author VectorCode
 *
 */
@Service
public class ConfirmationTokenService {
	
	// List all repository to use
	private final ConfirmationTokenRepository confirmationTokenRepository;

	
	public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
		super();
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

	// Saved a confirmation token on the Dbs
	public String saveConfirmationToken(ConfirmationToken token) {
		
		//Check if null and return an err
		if (token == null) {
			return "Token could not be null!";
		}
		
		confirmationTokenRepository.save(token);
		
		return null;
	}

	// Return a token if exist
	public Optional<ConfirmationToken> getToken(String token) {
		
		//Check if null and return an Optional empty
		if (token == null || token.isEmpty()) {
			return Optional.ofNullable(null);
		}
		
		return confirmationTokenRepository.findByToken(token);
	}

	// Update the date of the confirmation
	public String setConfirmedAt(String token) {
		
		//Check if null and return an err
		if (token == null || token.isEmpty()) {
			return "Token could not be null or empty";
		}
		
		confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
		
		return null;
	}

}
