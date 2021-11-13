package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class UserService implements UserDetailsService {

	// List all repository to use
	private final UserRepository userRepository;
	private final UserActivityService userActivityService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;

	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			ConfirmationTokenService confirmationTokenService, UserActivityService userActivityService) {
		super();
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
		this.confirmationTokenService = confirmationTokenService;
		this.userActivityService = userActivityService;
	}

	// Return a list of AppUsers
	public List<AppUser> getUsers() {
		return userRepository.findAll();
	}

	// Remove a user, if exitst, by userId
	@Transactional
	public String removeUser(long userId) {

		Optional<AppUser> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			userRepository.deleteById(userId);
			return null;
		} else {
			return "User with Id: " + userId + " does not exist!";
		}

	}

	// Login a user by username (email)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Unable to find user with e: " + email));
	}

	// Register a new user
	public String signUpUser(AppUser user) {

		// Check if user already exist
		Optional<AppUser> optionalUser = userRepository.findByEmail(user.getEmail());

		if (optionalUser.isPresent()) {

			if (!optionalUser.get().isEnabled()) {

				// Delete old user registration
				userRepository.delete(optionalUser.get());

				// Create another one
				String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

				user.setPassword(encodedPassword);

				userRepository.save(user);

				// Generate the confirmation token
				String tokenString = UUID.randomUUID().toString();

				ConfirmationToken token = new ConfirmationToken(tokenString, LocalDateTime.now(),
						LocalDateTime.now().plusMinutes(15), user);

				// Save the token
				confirmationTokenService.saveConfirmationToken(token);

				return tokenString;
			}

			throw new IllegalStateException("Email already in use! " + user.getEmail());
		}

		// Create and save a new user
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);

		userRepository.save(user);

		// Generate the confirmation token
		String tokenString = UUID.randomUUID().toString();

		ConfirmationToken token = new ConfirmationToken(tokenString, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15), user);

		// Save the token
		confirmationTokenService.saveConfirmationToken(token);

		return tokenString;

	}

	// Enable a user by user email
	public String enableAppUser(String email) {

		Optional<AppUser> optional = userRepository.findByEmail(email);

		if (optional.isEmpty()) {
			return "Unable to enable the app user account! It doesnt exists!";
		} else {
			userRepository.enableAppUser(email);
		}

		return null;
	}

	// Return a list of App Users of type Studenti
	public List<AppUser> getAllAppUserStudenti() {

		return userRepository.findAllByUserType(UserType.STUDENTE);
	}

	// Return a list of App Users of type Tutor
	public List<AppUser> getAllAppUserTutor() {

		return userRepository.findAllByUserType(UserType.TUTOR);
	}

	// Return a list of App Users STUDENTI credits
	public List<StudentCredits> getAllUsersCredits() {
		return computeUserCredits(getAllAppUserStudenti());
	}

	private List<StudentCredits> computeUserCredits(List<AppUser> appUserStudenti) {

		List<StudentCredits> usersCreditsList = new ArrayList<StudentCredits>();

		// For each studente compute total credits
		for (AppUser student : appUserStudenti) {

			// Get a list of user's activity
			ArrayList<UserActivity> list = new ArrayList<>();

			userActivityService.listAllActivitiesOfUser(student.getEmail()).iterator().forEachRemaining(list::add);

			double count1 = 0;
			double count2 = 0;

			// Genereate a pojo to carry them
			StudentCredits studentCredits = new StudentCredits();
			studentCredits.setUser(student);

			// For each user'activity count approve and not approve credits
			for (UserActivity userActivity : list) {

				// If the user is the organizer of the activity set + 0.2 credits
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

			count1 = (count1 / 4.5) * 100;
			count1 = (double) Math.round(count1 * 100) / 100;
			studentCredits.setApprovedCredits(count1);

			count2 = (count2 / 4.5) * 100;
			count2 = (double) Math.round(count2 * 100) / 100;
			studentCredits.setNotApprovedCredits(count2);

			usersCreditsList.add(studentCredits);
		}

		return usersCreditsList;
	}

	// Return a list of tutor's mentee
	public List<AppUser> getAllMenteeByTutor(AppUser tutor) {
		return userRepository.findAllByTutor(tutor);
	}

	// Return a list of tutor's mentee credits
	public List<StudentCredits> getAllMenteeCreditsByTutor(AppUser tutor) {
		return computeUserCredits(getAllMenteeByTutor(tutor));
	}

	// Update User Tutor
	@Transactional
	public String updateUserTutor(Long userId, String tutorId) {

		Optional<AppUser> userOptional = userRepository.findById(userId);
		Optional<AppUser> userTutorOptional = userRepository.findById(Long.valueOf(tutorId));

		// Check if user exists
		if (userOptional.isPresent() && userTutorOptional.isPresent()) {
			userOptional.get().setTutor(userTutorOptional.get());
			return null;
		} else {
			return "Error! User or Tutor doesnt exists!";
		}

	}

	// Update User Type
	@Transactional
	public String updateUserType(Long userId, String type) {

		Optional<AppUser> userOptional = userRepository.findById(userId);

		// Check if user exists
		if (userOptional.isPresent()) {
			userOptional.get().setUserType(UserType.valueOf(type));
			return null;
		} else {
			return "Error! User doesnt exists!";
		}
	}

}
