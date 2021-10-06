package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.User;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	public User addUser(User user) {
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		
		if (optionalUser.isPresent()) {
			throw new IllegalStateException("Email already in use! " + user.getEmail());
		}
		
		return userRepository.save(user);
	}

	public void removeUser(long userId) {
		userRepository.deleteById(userId);
	}

	@Transactional
	public User updateUser(long userId, String name, String surname, String email) {
		
		Optional<User> optionalUser = userRepository.findById(userId);
		
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
			
			Optional<User> optionalUser2 = userRepository.findByEmail(email);
			
			if (optionalUser2.isPresent()) {
				throw new IllegalStateException("Email already in use! " + email);
			}
			
			optionalUser.get().setEmail(email);
		}
		
		return optionalUser.get();
	}

	public User addUserExample() {
		
		Random r = new Random();
		
		long random = (r.nextLong());
		
		User user = new User("Name" + random, "Surname" + random, random + "@gmail.com", random+"", UserType.STUDENTE);
		
		return userRepository.save(user);
	}
	
}
