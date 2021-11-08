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
		userActivity.setApproved(false);
		
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
	
	public List<UserActivity> listAllActivitiesOfUser(String user) {
		
		Optional<AppUser> userOptional = userRepository.findByEmail(user);
		return userActivityRepository.findByUser(userOptional.get());
		
	}
	
	public Optional<UserActivity> getUserActivityByUserAndActivityAndOrganizer(String user, long activityId, boolean organizer) {
		Optional<AppUser> userOptional = userRepository.findByEmail(user);
		Optional<Activity> activityOptional = activityRepository.findById(activityId);
		
		return userActivityRepository.findByUserAndActivityAndOrganizer(userOptional.get(), activityOptional.get(), organizer);
	}

	public List<UserActivity> listAllUserActivitiesToApprove(Long userId) {
		
		return userActivityRepository.findByUserAndApproved(userRepository.findById(userId).get(), false);
	}
	
	@Transactional
	public void updateUserActivityState(Long userActivityId) {
		
		Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userActivityId);
		
		if( userActivityOptional.isPresent()) {
			userActivityOptional.get().setApproved(true);
		} else {
			throw new IllegalStateException("User Activity does not exist!");
		}
		
	}

	public double getTotalApprovedCredits(AppUser user) {
		
		ArrayList<UserActivity> list = new ArrayList<>();
		listAllActivitiesOfUser(user.getEmail()).iterator().forEachRemaining(list::add);
		
		double count1 = 0;
		
		for (UserActivity userActivity : list) {
			
			if (userActivity.isOrganizer()) {
				
				if (userActivity.isApproved()) {
					count1 = count1 + 0.2;
				}
				
			} else {
				
				if (userActivity.isApproved()) {
					count1 = count1 + userActivity.getActivity().getActivityCredits().getVal();
				}
				
			}
		}
		
		count1 = (count1/4.5)*100;
		count1 = (double) Math.round(count1 * 100) / 100;

		return count1;
	}
	
	public double getTotalToBeApprovedCredits(AppUser user) {
		
		ArrayList<UserActivity> list = new ArrayList<>();
		listAllActivitiesOfUser(user.getEmail()).iterator().forEachRemaining(list::add);
		
		double count2 = 0;
		
		for (UserActivity userActivity : list) {
			
			if (userActivity.isOrganizer()) {
				
				if (!userActivity.isApproved()) {
					count2 = count2 + 0.2;
				}
				
			} else {
				
				if (!userActivity.isApproved()) {
					count2 = count2 + userActivity.getActivity().getActivityCredits().getVal();
				}
				
			}
		}
		
		count2 = (count2/4.5)*100;
		count2 = (double) Math.round(count2 * 100) / 100;
		
		return count2;
	}
	
}
