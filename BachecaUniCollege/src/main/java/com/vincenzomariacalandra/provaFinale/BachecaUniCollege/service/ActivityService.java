package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.Optional;

import org.springframework.data.convert.JodaTimeConverters.DateTimeToDateConverter;
import org.springframework.stereotype.Service;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;

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
				activityRepository.findById(activity.getId());
		
		if (activityOptional.isPresent()) {
			throw new IllegalStateException("Utente già presente!");
		}
		
		activityRepository.save(activity);
		return true;
	}

	public boolean deleteActivity(long id) {
		
		Optional<Activity> activityOptional =
				activityRepository.findById(id);
		
		if (activityOptional.isPresent()) {
			activityRepository.deleteById(id);
			return true;
			
		} else {
			throw new IllegalStateException("Utente non presente");
		}
	}
	
	public void createExampleActivity () {
		
		
		
		Activity activity = new Activity("Titolo", false, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2021, 11, 11)), 
				Time.valueOf(LocalTime.now()) , Time.valueOf(LocalTime.of(10, 10)), ActivityType.VOLONTARIATO, ActivityCredits.TWO);
		
		activityRepository.save(activity);
	}
	
	
}
