package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.util.List;

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
	
	
}
