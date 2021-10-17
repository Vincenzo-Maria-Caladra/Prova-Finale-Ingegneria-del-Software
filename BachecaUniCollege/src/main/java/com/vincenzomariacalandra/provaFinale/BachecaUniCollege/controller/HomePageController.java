package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;

@Controller
@RequestMapping("/homePage")
public class HomePageController {
	
	private final ActivityService activityService;
	
	
	@Autowired
	public HomePageController(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	@GetMapping
	public String listAllActivities(Model model) {
		
		ArrayList<Activity> list = new ArrayList<>();
		
		activityService.getActivities().iterator().forEachRemaining(list::add);
		
		model.addAttribute("activities", list);
		
		return "homePage";
	}
	
}
