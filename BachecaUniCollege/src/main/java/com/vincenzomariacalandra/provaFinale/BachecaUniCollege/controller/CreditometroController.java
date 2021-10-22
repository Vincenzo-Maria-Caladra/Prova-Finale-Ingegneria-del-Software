package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

/**
 * @author CalandraVM
 * Classe Controller per la pagina creditometro.html
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
	
	@GetMapping
	public String getCreditometro(Model model, HttpServletRequest request, Authentication authentication) {
		
		String user = request.getUserPrincipal().getName();
		
		ArrayList<UserActivity> list = new ArrayList<>();
		userActivityService.listAllActivitiesOfUser(user).iterator().forEachRemaining(list::add);
		model.addAttribute("userActivities", list);
		
		double count1 = 0;
		double count2 = 0;
		
		for (UserActivity userActivity : list) {
			
			if (userActivity.isOrganizer()) {
				
				if (userActivity.isApproved()) {
					count1 = count1 + 0.2;
				} else {
					count2 = count2 + 0.2;
				}
				
			} else {
				
				if (userActivity.isApproved()) {
					count1 = count1 + userActivity.getActivity().getActivityCredits().getVal();
				} else {
					count2 = count2 + userActivity.getActivity().getActivityCredits().getVal();
				}
				
			}
		}
		
		count1 = (count1/4.5)*100;
		count1 = (double) Math.round(count1 * 100) / 100;
		model.addAttribute("count1", count1 );
		
		count2 = (count2/4.5)*100;
		count2 = (double) Math.round(count2 * 100) / 100;
		model.addAttribute("count2", count2 );
		
		
		return "creditometro";
	}
	
}
