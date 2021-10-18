package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;

@Service
public class UserActivityService {

	private final UserActivityRepository userActivityRepository;
	private final ActivityRepository activityRepository;
	private final UserRepository userRepository;

	@Autowired
	public UserActivityService(UserActivityRepository userActivityRepository, ActivityRepository activityRepository,
			UserRepository userRepository) {
		super();
		this.userActivityRepository = userActivityRepository;
		this.activityRepository = activityRepository;
		this.userRepository = userRepository;
	}

	public UserActivity insertNewUserActivity(long idUser, long idActivity, boolean organizer) {

		Optional<Activity> activityOptional = activityRepository.findById(idActivity);
		Optional<AppUser> userOptional = userRepository.findById(idUser);

		if (activityOptional.isEmpty()) {
			throw new IllegalStateException("Unknow activity with id:" + idActivity);
		}

		if (userOptional.isEmpty()) {
			throw new IllegalStateException("Unknow user with id:" + idActivity);
		}

		if (organizer) {
			Optional<UserActivity> userActivityOptional = userActivityRepository
					.findByActivityAndOrganizer(activityOptional.get(), organizer);

			if (userActivityOptional.isPresent()) {
				throw new IllegalStateException("An Organizer is already set for actvity with id:" + idActivity);
			}
		}

		UserActivity userActivity = new UserActivity(userOptional.get(), activityOptional.get(), organizer);

		userActivityRepository.save(userActivity);
		
		return userActivity;
	}
	
	
	public void deleteUserActivityByActivityId(AppUser appUser, Activity activity) {
		
		Optional<UserActivity> userActivityOptional = userActivityRepository.findByUserAndActivityAndOrganizer(appUser, activity, false);
		
		userActivityRepository.delete(userActivityOptional.get());  
	}
	
	public List<UserActivity> listAllUserActivities() {
		return userActivityRepository.findAll();
	}
	
	public List<UserActivity> listAllUserOfOneActivity(Activity activity){
		return userActivityRepository.findByActivity(activity);
	}
	
	public Optional<UserActivity> getUserActivityByUserAndActivityAndOrganizer(String user, long activityId, boolean organizer) {
		Optional<AppUser> userOptional = userRepository.findByEmail(user);
		Optional<Activity> activityOptional = activityRepository.findById(activityId);
		
		return userActivityRepository.findByUserAndActivityAndOrganizer(userOptional.get(), activityOptional.get(), organizer);
	}
	
}
