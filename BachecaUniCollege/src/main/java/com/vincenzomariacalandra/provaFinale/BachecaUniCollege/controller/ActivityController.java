package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

@Controller
public class ActivityController {
	
	private final ActivityService activityService;
	
	@Autowired
	public ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	@RequestMapping(path = "/homePage", method = RequestMethod.GET)
	public String listAllActivities(Model model) {
		
		ArrayList<Activity> list = new ArrayList<>();
		
		activityService.getActivities().iterator().forEachRemaining(list::add);
		
		model.addAttribute("activities",list);
		
		return "homePage";
	}
	

	public Activity registerNewActivity(@RequestBody Activity activity) {
		return activityService.addNewActivity(activity);
	}
	
	//@DeleteMapping(path = "/{activityId}")
	public void deleteActivity(@PathVariable("activityId") long activityId) {
		activityService.deleteActivity(activityId);
	}
	
	//@PutMapping(path = "/{activityId}")
	public Activity updateActivity(@PathVariable("activityId") long activityId,
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
	
	@RequestMapping(path = "/homePage/nuovaAttivita", method = RequestMethod.GET)
	public String creaNuovaAttivitaPage (Model model) {
		model.addAttribute("newActivity", new Activity());
		return "creaAttivita";
	}
	
	@RequestMapping(method =RequestMethod.POST, path = "/homePage/nuovaAttivita")
	public String addExampleActivity (@ModelAttribute Activity activity, Model model) {
		activityService.addNewActivity(activity);
		model.addAttribute("msg", "Attività aggiunta con successo! Attendi che venga approvata");
		return "homePage";
	}
}
