package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;

/**
 * @author VectorCode
 *
 */
@Controller
@RequestMapping("/homeBacheca")
public class HomeBachecaController {
	
	// All Services required
	private final ActivityService activityService;
	
	@Autowired
	public HomeBachecaController(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	// Initialization of homeBacheca 
	@GetMapping
	public String listAllActivities(Model model) {
		
		//Add list attribute to the model
		ArrayList<Activity> list = new ArrayList<>();
		activityService.getActivitiesApproved().iterator().forEachRemaining(list::add);
		model.addAttribute("activities", list);
		
		return "homeBacheca";
	}
	
}
