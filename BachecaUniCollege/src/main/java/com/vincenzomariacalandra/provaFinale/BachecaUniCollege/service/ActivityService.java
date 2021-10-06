package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.convert.JodaTimeConverters.DateTimeToDateConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Activity addNewActivity(Activity activity) {
		
		Optional<Activity> activityOptional =
				activityRepository.findById(activity.getId());
		
		if (activityOptional.isPresent()) {
			throw new IllegalStateException("Utente già presente!");
		}
		
		activityRepository.save(activity);
		return activity;
	}

	public void deleteActivity(long id) {
		
		Optional<Activity> activityOptional =
				activityRepository.findById(id);
		
		if (activityOptional.isPresent()) {
			activityRepository.deleteById(id);
			
		} else {
			throw new IllegalStateException("Utente non presente");
		}
	}
	
	public Activity createExampleActivity () {
		
		Activity activity = new Activity("Titolo", false, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2021, 11, 11)), 
				Time.valueOf(LocalTime.now()) , Time.valueOf(LocalTime.of(10, 10)), ActivityType.VOLONTARIATO, ActivityCredits.TWO);
		
		return activityRepository.save(activity);
	}

	@Transactional
	public Activity updateActivity(long activityId, String title, Boolean state, Date startDate, Date endDate, Time startTime,
			Time endTime, ActivityType activityType, ActivityCredits activityCredits) {
		
		Activity activity = activityRepository.findById(activityId).orElseThrow( () -> new IllegalStateException(
				"Activity with id:" + activityId + " not found!"));
		
		if (title != null &&
				title.length() > 0 &&
				!Objects.equals(activity.getTitle(), title)){
					activity.setTitle(title);
				}
		
		if (state != null &&
				!Objects.equals(activity.isState(), state)) {
			activity.setState(state);
		}
		
		if ( startDate != null && endDate == null &&
				! startDate.before(Date.valueOf(LocalDate.now())) &&
				! startDate.after(activity.getEndDate())) {
			activity.setStartDate(startDate);
		}
		
		if ( endDate != null && startDate == null &&
				! endDate.before(Date.valueOf(LocalDate.now())) &&
		        ! endDate.before(activity.getStartDate())) {
			activity.setEndDate(endDate);
		}
		
		if (startDate != null && endDate != null &&
				! startDate.before(Date.valueOf(LocalDate.now())) &&
				! endDate.before(Date.valueOf(LocalDate.now())) &&
				startDate.before(endDate)) {
			activity.setStartTime(startTime);
			activity.setEndDate(endDate);
		}
		
		if (startTime != null && endTime == null &&
				! startTime.before(Time.valueOf(LocalTime.now())) &&
				! startTime.after(activity.getEndTime())) {
			activity.setStartTime(startTime);
		}
		
		if (endTime != null && startTime == null &&
				! endTime.before(Time.valueOf(LocalTime.now())) &&
				! endTime.before(activity.getStartTime())) {
			activity.setEndTime(endTime);
		}
		
		if (startTime != null && endTime != null &&
				! startTime.before(Time.valueOf(LocalTime.now())) &&
				! endTime.before(Time.valueOf(LocalTime.now())) &&
				! startTime.before(endTime)) {
			activity.setStartTime(startTime);
			activity.setEndTime(endTime);
		}
		
		if (activityType != null &&
				! Objects.equals(activityType, activity.getActivityType())) {
			activity.setActivityType(activityType);
		}
		
		if (activityCredits != null &&
				! Objects.equals(activityCredits, activity.getActivityCredits())) {
			activity.setActivityCredits(activityCredits);
		}
		
		return activity;
	}
	
	
}
