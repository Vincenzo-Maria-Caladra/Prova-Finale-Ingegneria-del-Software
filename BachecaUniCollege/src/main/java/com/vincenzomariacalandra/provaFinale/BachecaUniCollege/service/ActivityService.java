package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
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
	
	public Optional<Activity> findActivityById(Long id) {
		
		return activityRepository.findById(id);
	}

	public Activity addNewActivity(Activity activity) {
		
		activity.setState(false);
		
		activityRepository.save(activity);
		
		return activity;
	}

	public void deleteActivity(long id) {
		
		Optional<Activity> activityOptional =
				activityRepository.findById(id);
		
		if (activityOptional.isPresent()) {
			activityRepository.deleteById(id);
			
		} else {
			throw new IllegalStateException("Activity does not exist!");
		}
	}

	@Transactional
	public Activity updateActivity(long activityId, String title, Boolean state, Date startDate, Date endDate, String startTimeS,
			String endTimeS, ActivityType activityType, ActivityCredits activityCredits) {
		
		Time startTime = Time.valueOf(LocalTime.parse(startTimeS));
		Time endTime = Time.valueOf(LocalTime.parse(endTimeS));
		
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
			activity.setStartTime(startTimeS);
			activity.setEndDate(endDate);
		}
		
		if (startTime != null && endTime == null &&
				! startTime.before(Time.valueOf(LocalTime.now())) &&
				! startTime.after(activity.getEndTime())) {
			activity.setStartTime(startTimeS);
		}
		
		if (endTime != null && startTime == null &&
				! endTime.before(Time.valueOf(LocalTime.now())) &&
				! endTime.before(activity.getStartTime())) {
			activity.setEndTime(endTimeS);
		}
		
		if (startTime != null && endTime != null &&
				! startTime.before(Time.valueOf(LocalTime.now())) &&
				! endTime.before(Time.valueOf(LocalTime.now())) &&
				! startTime.before(endTime)) {
			activity.setStartTime(startTimeS);
			activity.setEndTime(endTimeS);
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
	
	
	@Transactional
	public Activity updateActivity(long activityId, Date startDate, Time startTime, Time endTime) {
				
		Activity activity = activityRepository.findById(activityId).orElseThrow( () -> new IllegalStateException(
				"Activity with id:" + activityId + " not found!"));
		
		if(startDate.before(Date.valueOf(LocalDate.now()))) {
			System.out.println(startDate);
			System.out.println(Date.valueOf(LocalDate.now()));
			throw new IllegalStateException("Data inserita antecedente ad oggi!");
		} else {
			
			if (startTime.after(endTime)) {
				throw new IllegalStateException("Ora di inizio successiva all'ora di fine!");
			}
			
			activity.setStartDate(startDate);
			activity.setStartTime(startTime.toString());
		}
		
		return activity;
	}
	
	@Transactional
	public Activity updateActivityState (Long id) {
		
		Optional<Activity> activityOptional = activityRepository.findById(id);
		
		if(activityOptional.isPresent()) {
			activityOptional.get().setState(true);
		}
		
		return activityOptional.get();
		
	}
	
	public Iterable<Activity> getActivitiesToApprove(){
		
		return activityRepository.findByStateAndActivityTypeNot(false, ActivityType.LIBRO);
	}
	
	public Iterable<Activity> getActivitiesApproved(){
		
		return activityRepository.findByStateAndActivityTypeNot(true, ActivityType.LIBRO);
	}

	public List<Activity> getAllTertulieToBeApproved() {
		
		return activityRepository.findAllByActivityTypeAndState(ActivityType.TERTULIA_A_TEMA, Boolean.FALSE);
	}
	
	
}
