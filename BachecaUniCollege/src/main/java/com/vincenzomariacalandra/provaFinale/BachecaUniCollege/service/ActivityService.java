package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;


/**
 * @author VectorCode
 *
 */
@Service
public class ActivityService {

	//List all repository to use
	private final ActivityRepository activityRepository;
	
	@Autowired
	public ActivityService(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}
	
	//Return an activity
	public Optional<Activity> findActivityById(Long id) {
		
		if (activityRepository.findById(id).isEmpty()) {
			throw new NullPointerException("Activity not found" + id);
		}
		
		return activityRepository.findById(id);
	}

	//Add a new activity
	public String addNewActivity(Activity activity) {
		
		// Check for errors
		String err = activityValidator(activity);
		
		if (err != null) {
			
			return err;
			
		} else {
			
			activity.setState(false);
			activityRepository.save(activity);
		}
		
		return null;
	}
	
	//Activity validation functions
	private String activityValidator(Activity activity) {
		
		if (activity == null) {
			return "Invalid activity!";
		}
		else if (activity.getTitle() == null || activity.getTitle().equals("")) {
			return "Title should not be empty!";
		} else if (activity.getDescrizione() == null || activity.getDescrizione().equals("") ) {
			return "Description should not be empty!";
		} else if (activity.getStartDate().after(activity.getEndDate())) {
			return "Start date should not be after end date!";
		} else if (activity.getStartTime().after(activity.getEndTime())) {
			return "Start time should not be after end time!";
		}
		
		return null;
	}

	//Delete activity function
	public String deleteActivity(long id) {
		
		
		Optional<Activity> activityOptional =
				activityRepository.findById(id);
		
		if (activityOptional.isPresent()) {
			activityRepository.deleteById(id);
			return null;
			
		} else {
			
			return "Activity does not exist!";
			
		}
		
	}
	
	//Update activity function
	@Transactional
	public String updateActivity(long activityId, Date startDate, Time startTime, Time endTime) {
				
		Activity activity = activityRepository.findById(activityId).orElseThrow( () -> new IllegalStateException(
				"Activity with id:" + activityId + " not found!"));
		
		if(startDate.before(Date.valueOf(LocalDate.now()))) {
			
			return "Data inserita antecedente ad oggi!";
			
		} else {
			
			activity.setStartDate(startDate);
			activity.setStartTime(startTime.toString());
			
			if (activityValidator(activity) != null) {
				return activityValidator(activity);
			}
			
		}
		
		return null;
	}
	
	// Update activity state function
	@Transactional
	public String updateActivityState (Long id) {
		
		Optional<Activity> activityOptional = activityRepository.findById(id);
		
		if(activityOptional.isPresent()) {
			activityOptional.get().setState(true);
			return null;
		}
		
		return "Activity not found!";
		
	}
	
	// Return a list activities to be approved, without book
	public List<Activity> getActivitiesToApprove(){
		
		return activityRepository.findByStateAndActivityTypeNot(false, ActivityType.LIBRO);
	}
	
	// Return a list activities approved, without book
	public List<Activity> getActivitiesApproved(){
		
		return activityRepository.findByStateAndActivityTypeNot(true, ActivityType.LIBRO);
	}
	
	// Return a list of tertulie a tema to be approved
	public List<Activity> getAllTertulieToBeApproved() {
		
		return activityRepository.findAllByActivityTypeAndState(ActivityType.TERTULIA_A_TEMA, Boolean.FALSE);
	}
	
	
}
