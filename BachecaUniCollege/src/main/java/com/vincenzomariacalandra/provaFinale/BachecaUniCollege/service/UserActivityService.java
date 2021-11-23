package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;

/**
 * @author VectorCode
 *
 */
@Service
public class UserActivityService {

	// List all repository to use
	private final UserActivityRepository userActivityRepository;
	private final ActivityRepository activityRepository;
	private final UserRepository userRepository;
	
	private final double TOTAL_CREDITS = 4.5;

	public UserActivityService(UserActivityRepository userActivityRepository, ActivityRepository activityRepository,
			UserRepository userRepository) {
		super();
		this.userActivityRepository = userActivityRepository;
		this.activityRepository = activityRepository;
		this.userRepository = userRepository;
	}

	// Return null if everything goes well or an err message
	// Insert a new User activity
	public String insertNewUserActivity(Long idUser, Long idActivity, Boolean organizer) {

		String err;
		
		// Check params
		if (idUser == null || idActivity == null || organizer == null) {
			err = "Invalid arguments for a new user activity";
			return err;
		}
		
		//Check if user and activity exist
		Optional<Activity> activityOptional = activityRepository.findById(idActivity);
		Optional<AppUser> userOptional = userRepository.findById(idUser);

		if (activityOptional.isEmpty()) {
			err = "Unknow activity with id:" + idActivity;
			
			return err;
		}

		if (userOptional.isEmpty()) {
			err = "Unknow user with id:" + idActivity;
			
			return err;
		}

		//If organizer field is true check if already an organizer have been set for the activity
		if (organizer) {
			
			Optional<UserActivity> userActivityOptional = userActivityRepository
					.findByActivityAndOrganizer(activityOptional.get(), organizer);

			if (userActivityOptional.isPresent()) {
				
				err = "An Organizer is already set for actvity with id:" + idActivity;
				return err;
			}
		}

		// Create a new user Activity and set approved field to false
		UserActivity userActivity = new UserActivity(userOptional.get(), activityOptional.get(), organizer);
		userActivity.setApproved(false);
		
		// Save the userActivity
		userActivityRepository.save(userActivity);
		
		return null;
	}
	
	// Delete an user activity by id
	// and return a err if something goes wrong
	public String deleteUserActivityByActivityId(AppUser appUser, Activity activity) {
		
		String err;
		
		// Check parameters passed
		if (appUser == null || activity == null) {
			err = "User or activity are invalid or null object!";
			return err;
		}
		
		Optional<UserActivity> userActivityOptional = userActivityRepository.findByUserAndActivityAndOrganizer(appUser, activity, false);
		
		// Check if the user activity exist
		if (userActivityOptional.isEmpty()) {
			err = "User activity not found!";
			return err;
		}
		
		// Delete the activity
		userActivityRepository.delete(userActivityOptional.get());
		
		return null;
	}
	
	// Return a list of User Activities
	public List<UserActivity> listAllUserActivities() {
		return userActivityRepository.findAll();
	}
	
	// Return a list of users subscribed to an activity
	public List<UserActivity> listAllUserOfOneActivity(Activity activity){
		return userActivityRepository.findByActivity(activity);
	}
	
	// Return a list of activities of a specified user
	public List<UserActivity> listAllActivitiesOfUser(String user) {
		
		Optional<AppUser> userOptional = userRepository.findByEmail(user);
		return userActivityRepository.findByUser(userOptional.get());
		
	}
	
	// Return an User Activity with specific characteristic
	public Optional<UserActivity> getUserActivityByUserAndActivityAndOrganizer(String user, Long activityId, Boolean organizer) {
		
		Optional<AppUser> userOptional = userRepository.findByEmail(user);
		Optional<Activity> activityOptional = activityRepository.findById(activityId);
		return userActivityRepository.findByUserAndActivityAndOrganizer(userOptional.get(), activityOptional.get(), organizer);
	}

	// Return a List of user activities to approve
	public List<UserActivity> listAllUserActivitiesToApprove(Long userId) {
		
		Optional<AppUser> userOptional = userRepository.findById(userId);
		return userActivityRepository.findByUserAndApproved(userOptional.get(), false);
	}
	
	// Update a specified user activity
	@Transactional
	public String updateUserActivityState(Long userActivityId) {
		
		//Check if the user activity exist
		Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userActivityId);
		
		if( userActivityOptional.isPresent()) {
			
			// Set approved attribute to true
			userActivityOptional.get().setApproved(true);
			
			return null;
			
		} else {
			
			//If not exist throw new IllegalStateEx
			throw new IllegalStateException("User Activity does not exist!");
		}
		
	}

	// Return the total of credits approved 
	public double getTotalApprovedCredits(AppUser user) {
		
		//Return a list of activities of a specified user 
		ArrayList<UserActivity> list = new ArrayList<>();
		listAllActivitiesOfUser(user.getEmail()).iterator().forEachRemaining(list::add);
		
		//Initialized count
		double count1 = 0;
		
		// For each user activity in the list do the follow:
		for (UserActivity userActivity : list) {
			
			// Check if is the organizer of the activity
			if (userActivity.isOrganizer()) {
				
				// If approved ad +0.2
				if (userActivity.isApproved()) {
					count1 = count1 + 0.2;
				}
				
			} else {
				
				// If not the organizer and if it is approved 
				if (userActivity.isApproved()) {
					
					// Get the credits of the user activity and add to the count
					count1 = count1 + userActivity.getActivity().getActivityCredits().getVal();
				}
				
			}
		}
		
		// Compute the %
		count1 = (count1/TOTAL_CREDITS)*100;
		count1 = (double) Math.round(count1 * 100) / 100;

		return count1;
	}
	
	// Return the total of credits not approved
	public double getTotalToBeApprovedCredits(AppUser user) {
		
		//Return a list of activities of a specified user 
		ArrayList<UserActivity> list = new ArrayList<>();
		listAllActivitiesOfUser(user.getEmail()).iterator().forEachRemaining(list::add);
		
		//Initialized count
		double count2 = 0;
		
		// For each user activity in the list do the follow:
		for (UserActivity userActivity : list) {
			
			// Check if is the organizer of the activity
			if (userActivity.isOrganizer()) {
				
				// Check if not approved
				if (!userActivity.isApproved()) {
					
					// If approved ad +0.2
					count2 = count2 + 0.2;
				}
				
			} else {
				
				// If not the organizer and if it is not approved 
				if (!userActivity.isApproved()) {
					
					// Get the credits of the user activity and add to the count
					count2 = count2 + userActivity.getActivity().getActivityCredits().getVal();
				}
				
			}
		}
		
		// Compute the %
		count2 = (count2/TOTAL_CREDITS)*100;
		count2 = (double) Math.round(count2 * 100) / 100;
		
		return count2;
	}

	// Add a new book user activity from already exist book activity
	public String addAJustRedBook(Long id, AppUser appUser) {
		
		String err;
		
		// Check params
		if ( id == null || appUser == null) {
			err = "Activity id and user could not be null";
			return err;
		}
		
		Optional<Activity> optional = activityRepository.findById(id);
		
		// Check if book activity exists
		if (optional.isEmpty()) {
			err = "Activity with id " + id + " not found!";
			return err;
		}
		
		// Create a new user activity
		UserActivity userActivity = new UserActivity();
		userActivity.setActivity(optional.get());
		userActivity.setUser(appUser);
		
		// Save it 
		userActivityRepository.save(userActivity);
		
		return null;
	}
	
}
