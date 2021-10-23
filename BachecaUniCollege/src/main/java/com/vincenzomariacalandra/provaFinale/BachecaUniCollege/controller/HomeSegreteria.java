package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

@Controller
@RequestMapping("/homeSegreteria")
public class HomeSegreteria {
	
	// All Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public HomeSegreteria(ActivityService activityService, UserActivityService userActivityService, UserService userService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	@GetMapping
	public String getHomeSegreteria(Model model) {
		
		model.addAttribute("userCreditsList", userService.getAllUsersCredits());
		model.addAttribute("tertulieList", activityService.getAllTertulieToBeApproved());
		
		return "homeSegreteria";
	}
}
