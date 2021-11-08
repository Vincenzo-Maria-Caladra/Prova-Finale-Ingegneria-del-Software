package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.pojo.StudentCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

/**
 * @author VectorCode
 *
 */
@Service
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepository;
	private final UserActivityService userActivityService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService, UserActivityService userActivityService) {
		super();
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
		this.confirmationTokenService = confirmationTokenService;
		this.userActivityService = userActivityService;
	}
	
	public List<AppUser> getUsers() {
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
    
    
    public List<AppUser> getAllAppUserStudenti(){
    	
    	return userRepository.findAllByUserType(UserType.STUDENTE);
    }
    
    public List<AppUser> getAllAppUserTutor(){
    	
    	return userRepository.findAllByUserType(UserType.TUTOR);
    }
    
    
    public List<StudentCredits> getAllUsersCredits(){
    	
    	List<StudentCredits> usersCreditsList = new ArrayList<StudentCredits>();
    	
    	for (AppUser student : getAllAppUserStudenti()) {
    		
    		ArrayList<UserActivity> list = new ArrayList<>();
    		
    		userActivityService.listAllActivitiesOfUser(student.getEmail()).iterator().forEachRemaining(list::add);
    		
    		double count1 = 0;
    		double count2 = 0;
    		
    		StudentCredits studentCredits = new StudentCredits();
			studentCredits.setUser(student);
    		
    		for (UserActivity userActivity : list) {
    			
    			
    			
    			if (userActivity.isOrganizer()) {
    				
    				if (userActivity.isApproved()) {
    					count1 = count1 + 0.2;
    				} else {
    					count2 = count2 + 0.2;
    				}
    				
    			} else {
    				
    				if (userActivity.isApproved()) {
    					count1 = count1 + userActivity.getActivity().getActivityCredits().getVal();
    				} else {
    					count2 = count2 + userActivity.getActivity().getActivityCredits().getVal();
    				}
    				
    			}
    		}
    		
    		
    		
    		count1 = (count1/4.5)*100;
    		count1 = (double) Math.round(count1 * 100) / 100;
    		studentCredits.setApprovedCredits(count1);
    		
    		count2 = (count2/4.5)*100;
    		count2 = (double) Math.round(count2 * 100) / 100;
    		studentCredits.setNotApprovedCredits(count2);
    		
    		usersCreditsList.add(studentCredits);
    	}
    	
    	return usersCreditsList;
    	
    }
    
    public List<AppUser> getAllMenteeByTutor(AppUser tutor){
    	
    	return userRepository.findAllByTutor(tutor);
    }

	public List<StudentCredits> getAllMenteeCreditsByTutor(AppUser tutor) {

		
    	List<StudentCredits> usersCreditsList = new ArrayList<StudentCredits>();
    	
    	for (AppUser student : getAllMenteeByTutor(tutor)) {
    		
    		ArrayList<UserActivity> list = new ArrayList<>();
    		
    		userActivityService.listAllActivitiesOfUser(student.getEmail()).iterator().forEachRemaining(list::add);
    		
    		double count1 = 0;
    		double count2 = 0;
    		
    		StudentCredits studentCredits = new StudentCredits();
			studentCredits.setUser(student);
    		
    		for (UserActivity userActivity : list) {
    			
    			
    			
    			if (userActivity.isOrganizer()) {
    				
    				if (userActivity.isApproved()) {
    					count1 = count1 + 0.2;
    				} else {
    					count2 = count2 + 0.2;
    				}
    				
    			} else {
    				
    				if (userActivity.isApproved()) {
    					count1 = count1 + userActivity.getActivity().getActivityCredits().getVal();
    				} else {
    					count2 = count2 + userActivity.getActivity().getActivityCredits().getVal();
    				}
    				
    			}
    		}
    		
    		
    		
    		count1 = (count1/4.5)*100;
    		count1 = (double) Math.round(count1 * 100) / 100;
    		studentCredits.setApprovedCredits(count1);
    		
    		count2 = (count2/4.5)*100;
    		count2 = (double) Math.round(count2 * 100) / 100;
    		studentCredits.setNotApprovedCredits(count2);
    		
    		usersCreditsList.add(studentCredits);
    	}
    	
    	return usersCreditsList;
    	
	}
	
	@Transactional
	public void updateUserTutor(Long userId, String tutorId) {
		
		Optional<AppUser> userOptional =  userRepository.findById(userId);
		Optional<AppUser> userTutorOptional =  userRepository.findById(Long.valueOf(tutorId));
		
		if (userOptional.isPresent() && userTutorOptional.isPresent()) {
			userOptional.get().setTutor(userTutorOptional.get());
		}
		
	}
	
	@Transactional
	public void updateUserType(Long userId, String type) {
		
		Optional<AppUser> userOptional =  userRepository.findById(userId);
		
		if (userOptional.isPresent()) {
			
			userOptional.get().setUserType(UserType.valueOf(type));
		}
	}
    
	
}
