package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.request.RegistrationRequest;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.EmailValidator;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Service
public class RegistrationService {
	
	private final UserService userService;
	private final EmailValidator emailValidator;
	private final ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	public RegistrationService(EmailValidator emailValidator, UserService userService, ConfirmationTokenService confirmationTokenService) {
		super();
		this.userService = userService;
		this.emailValidator = emailValidator;
		this.confirmationTokenService = confirmationTokenService;
	}

	
	public String register(RegistrationRequest registrationRequest) {
		
		boolean isValidEmail = emailValidator.test(registrationRequest.getEmail());
		
		if (!isValidEmail) {
			throw new IllegalStateException("Your Email is not valid!");
		}
		
		
		
		return userService.signUpUser(
				new AppUser(
						registrationRequest.getName(),
						registrationRequest.getSurname(), 
						registrationRequest.getEmail(),
						registrationRequest.getPassword(), 
						UserType.STUDENTE));
	}
	
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        
        userService.enableAppUser( confirmationToken.getAppUser().getEmail());
        
        return "confirmed";
    }

    
}
