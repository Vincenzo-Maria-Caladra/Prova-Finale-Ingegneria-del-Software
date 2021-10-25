package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

@Controller
@RequestMapping("/homeTutor")
public class HomeTutorController {
	
	
	// All Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public HomeTutorController(ActivityService activityService, UserActivityService userActivityService, UserService userService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	@GetMapping
	public String getHomeTutor(Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser tutor = ((AppUser)principal);

		model.addAttribute("listOfMentee", userService.getAllMenteeByTutor(tutor));
		model.addAttribute("listOfUserCredits", userService.getAllMenteeCreditsByTutor(tutor));
		
		return "homeTutor";
	}
	
	@RequestMapping(path = "/dettaglioMentee", method = RequestMethod.GET)
	public String getDettaglioMentee(@RequestParam("id")Long id, Model model) {
		
		model.addAttribute("listOfActivitiesToApprove", userActivityService.listAllUserActivitiesToApprove(id));
		
		return "dettaglioMentee";
	}
	
	@RequestMapping(path = "/dettaglioMentee", method = RequestMethod.POST)
	public String updateMenteeActivityState(@RequestParam("id")Long userActivityId, Model model) {
		
		userActivityService.updateUserActivityState(userActivityId);
		
		return "redirect:/dettaglioMentee";
	}
	
}
