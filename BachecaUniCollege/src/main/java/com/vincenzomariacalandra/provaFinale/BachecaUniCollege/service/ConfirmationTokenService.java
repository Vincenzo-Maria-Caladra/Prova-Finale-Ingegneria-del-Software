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

	private final ConfirmationTokenRepository confirmationTokenRepository;

	public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
		super();
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

	public String saveConfirmationToken(ConfirmationToken token) {

		if (token == null) {
			return "Token could not be null!";
		}
		
		ConfirmationToken savedToken = confirmationTokenRepository.save(token);
		
		if (savedToken == null) {
			return "Saved token is null";
		}
		
		return null;
	}

	public Optional<ConfirmationToken> getToken(String token) {
		
		if (token == null) {
			return Optional.ofNullable(null);
		}
		
		return confirmationTokenRepository.findByToken(token);
	}

	public String setConfirmedAt(String token) {
		
		if (token == null || token.isEmpty()) {
			return "Token could not be null or empty";
		}
		
		confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
		
		return null;
	}

}
