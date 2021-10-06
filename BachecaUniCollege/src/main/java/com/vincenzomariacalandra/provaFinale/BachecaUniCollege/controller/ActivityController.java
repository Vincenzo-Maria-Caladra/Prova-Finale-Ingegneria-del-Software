package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.sql.Date;
import java.sql.Time;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;

@RestController
@RequestMapping(path = "api/v1/activity")
public class ActivityController {
	
	private final ActivityService activityService;
	
	@Autowired
	public ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	@GetMapping
	public Iterable<Activity> listAllActivities() {
		return activityService.getActivities();
	}
	
	@PostMapping
	public boolean registerNewActivity(@RequestBody Activity activity) {
		return activityService.addNewActivity(activity);
	}
	
	@DeleteMapping(path = "/{activityId}")
	public boolean deleteActivity(@PathVariable("activityId") long activityId) {
		return activityService.deleteActivity(activityId);
	}
	
	@PutMapping(path = "/{activityId}")
	public boolean updateActivity(@PathVariable("activityId") long activityId,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) boolean state,
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			@RequestParam(required = false) Time startTime,
			@RequestParam(required = false) Time endTime,
			@RequestParam(required = false) ActivityType activityType,
			@RequestParam(required = false) ActivityCredits activityCredits) {
		
		return activityService.updateActivity(activityId, title, state, startDate, endDate,
				startTime,endTime, activityType, activityCredits);
	}
	
	@RequestMapping(method =RequestMethod.POST, path = "/test")
	public void addExampleActivity () {
		activityService.createExampleActivity();
	}
}
