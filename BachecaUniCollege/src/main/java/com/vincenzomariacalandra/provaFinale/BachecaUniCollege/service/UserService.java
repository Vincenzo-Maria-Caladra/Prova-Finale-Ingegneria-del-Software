package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Service
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
		super();
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
		this.confirmationTokenService = confirmationTokenService;
	}
	
	public Iterable<AppUser> getUsers() {
		return userRepository.findAll();
	}

	public void removeUser(long userId) {
		
		Optional<AppUser> userOptional = userRepository.findById(userId);
		
		if (userOptional.isPresent()) {
			userRepository.deleteById(userId);
		} else {
			throw new IllegalStateException("User with Id: " + userId + " does not exist!");
		}
			
		
	}
	
	public Optional<AppUser> getUser(String email) {
		return userRepository.findByEmail(email);
	}


	@Transactional
	public AppUser updateUser(long userId, String name, String surname, String email) {
		
		Optional<AppUser> optionalUser = userRepository.findById(userId);
		
		if (optionalUser.isEmpty()) {
			throw new IllegalStateException("User: " + userId + " not found!");
		}
		
		if (name != null &&
				name.length() > 0 &&
				! Objects.equals(name, optionalUser.get().getName())) {
			optionalUser.get().setName(name);
		}
		
		if (surname != null &&
				surname.length() > 0 &&
				! Objects.equals(surname, optionalUser.get().getSurname())) {
			optionalUser.get().setSurname(surname);
		}
		
		if (email != null &&
				email.length() > 0 &&
				! Objects.equals(email, optionalUser.get().getEmail())) {
			
			Optional<AppUser> optionalUser2 = userRepository.findByEmail(email);
			
			if (optionalUser2.isPresent()) {
				throw new IllegalStateException("Email already in use! " + email);
			}
			
			optionalUser.get().setEmail(email);
		}
		
		return optionalUser.get();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow( () -> new UsernameNotFoundException("Unable to find user with e: " + email));
	}
	
	
	public String  signUpUser (AppUser user) {
		
		Optional<AppUser> optionalUser = userRepository.findByEmail(user.getEmail());
		
		if (optionalUser.isPresent()) {
			
			if (!optionalUser.get().isEnabled()) {
				throw new IllegalStateException("Confirm the token sent to: " + user.getEmail());
			}
			
			throw new IllegalStateException("Email already in use! " + user.getEmail());
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		
		userRepository.save(user);
		
		String tokenString = UUID.randomUUID().toString();
		
		ConfirmationToken token = new ConfirmationToken(
				tokenString,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				user);
		
		confirmationTokenService.saveConfirmationToken(token);
		
		
		return tokenString;
	
	}
	
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
	
}
