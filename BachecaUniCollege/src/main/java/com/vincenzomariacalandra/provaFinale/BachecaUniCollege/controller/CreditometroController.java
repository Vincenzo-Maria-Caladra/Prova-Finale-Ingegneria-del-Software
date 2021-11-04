package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;

/**
 * @author VectorCode
 *
 */
@Controller
@RequestMapping("/creditometro")
public class CreditometroController {
	
	// All Services required
	private final UserActivityService userActivityService;
	
	@Autowired
	public CreditometroController(UserActivityService userActivityService) {
		this.userActivityService = userActivityService;
	}
	
	//Creditometro page initialization
	@GetMapping
	public String getCreditometro(Model model, HttpServletRequest request, Authentication authentication) {
		
		//Retrieve useful information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser user = ((AppUser)principal);
		
		//Set credits
		double totalApprovedCredits = userActivityService.getTotalApprovedCredits(user);
		model.addAttribute("count1", totalApprovedCredits);
		
		double totalToBeApprovedCredits = userActivityService.getTotalToBeApprovedCredits(user);
		model.addAttribute("count2", totalToBeApprovedCredits);
		
		//Set list of user activity
		ArrayList<UserActivity> list = new ArrayList<>();
		userActivityService.listAllActivitiesOfUser(user.getEmail()).iterator().forEachRemaining(list::add);
		model.addAttribute("userActivities", list);

		
		return "creditometro";
	}
	
}
