package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
	
	private final ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
		super();
		this.confirmationTokenRepository = confirmationTokenRepository;
	}
	
	public void saveConfirmationToken(ConfirmationToken token) {
		 confirmationTokenRepository.save(token);
	}
	
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
	
	
}
