package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

@Controller
@RequestMapping("/homeDirettore")
public class HomeDirettoreController {
	
	// All Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public HomeDirettoreController(ActivityService activityService, UserActivityService userActivityService, UserService userService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	@GetMapping
	public String goToHomeDirettore(Model model) {
		
		model.addAttribute("listOfStudenti", userService.getAllAppUserStudenti());
		model.addAttribute("listOfTutor", userService.getAllAppUserTutor());
		
		return "homeDirettore";
	}
	
	@RequestMapping(path = "/deleteUser" , method = RequestMethod.POST)
	public String deleteStudente (@RequestParam Long userId, Model model) {
		
		userService.removeUser(userId);
		
		return "redirect:/homeDirettore";
	}
	
	@RequestMapping(path = "/updateTutor", method = RequestMethod.POST)
	public String updateTutorStudente (@RequestParam Long userId, @RequestParam("tutor") String tutorId, Model model) {
		
		userService.updateUserTutor(userId, tutorId);
		
		return "redirect:/homeDirettore";
	}
	

	
}
