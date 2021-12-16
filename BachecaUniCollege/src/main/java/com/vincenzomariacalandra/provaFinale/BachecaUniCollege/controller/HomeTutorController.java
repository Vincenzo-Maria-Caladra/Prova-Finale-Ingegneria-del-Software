package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

/**
 * @author VectorCode
 *
 */
@Controller
@RequestMapping("/homeTutor")
public class HomeTutorController {
	
	
	// All Services required
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public HomeTutorController(UserActivityService userActivityService, UserService userService) {
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	// Initialization of HomeTutor page
	@GetMapping
	public String getHomeTutor(@RequestParam(name = "mentee", required = false)Long id, Model model) {
		
		// Retrieve useful information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser tutor = ((AppUser)principal);
		
		// Add list attribute
		model.addAttribute("listOfUserCredits", userService.getAllMenteeCreditsByTutor(tutor));
		
		// Show list of actvity to approve when select
		if (id != null) {
			model.addAttribute("listOfActivitiesToApprove", userActivityService.listAllUserActivitiesToApprove(id));
		}
		
		return "homeTutor";
	}
	
	// Dettaglio mentee handler
	@RequestMapping(path = "/dettaglioMentee", method = RequestMethod.GET)
	public String getDettaglioMentee(@RequestParam("mentee")Long id, Model model) {
		
		return "redirect:/homeTutor?mentee=" + id;
	}
	
	// Update Mentee activity state handler
	@PostMapping
	public String updateMenteeActivityState(@RequestParam("userActivity")Long userActivityId, @RequestParam("userId")Long userId, Model model) {
		
		userActivityService.updateUserActivityState(userActivityId);
		
		return "redirect:/homeTutor?mentee=" + userId;
	}
	
}
