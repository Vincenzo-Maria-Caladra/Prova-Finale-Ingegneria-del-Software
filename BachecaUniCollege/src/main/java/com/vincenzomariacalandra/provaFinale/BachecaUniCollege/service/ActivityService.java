package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;

@Service
public class ActivityService {

	private final ActivityRepository activityRepository;
	
	public ActivityService(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}
	
	public Iterable<Activity> getActivities() {
		return activityRepository.findAll();
	}

	public boolean addNewActivity(Activity activity) {
		
		Optional<Activity> activityOptional =
				activityRepository.findById(activity.getActivity);
		
		if (activityOptional.isPresent()) {
			throw new IllegalStateException("Utente già presente!");
		}
		
		activityRepository.save(activity);
		
		return true;
	}

	public boolean deleteActivity(long id) {
		
		Optional<Activity> activityOptional =
				activityRepository.findById(activity);
		
		return false;
	}
	
	
}
