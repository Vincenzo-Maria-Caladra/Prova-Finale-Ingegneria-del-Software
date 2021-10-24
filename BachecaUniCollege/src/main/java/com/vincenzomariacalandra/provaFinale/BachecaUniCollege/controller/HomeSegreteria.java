package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
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
	public String getHomeSegreteria(@RequestParam(name = "id", required = false) Long id, Model model) {
		
		model.addAttribute("userCreditsList", userService.getAllUsersCredits());
		model.addAttribute("tertulieList", activityService.getAllTertulieToBeApproved());
		
		if (id != null) {
			model.addAttribute("form", Boolean.TRUE);
			model.addAttribute("updatedActivity", new Activity());
		} else {
			model.addAttribute("form", Boolean.FALSE);

		}
		
		return "homeSegreteria";
	}
	
	
	@RequestMapping(path = "/acceptTertulia", method = RequestMethod.POST)
	public String acceptTertuliaATema (@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		activityService.updateActivityState(id);
		
		return "redirect:/homeSegreteria";
	}
	
	@RequestMapping(path = "/deleteTertulia", method = RequestMethod.POST )
	public String deleteTertuliaATema (@RequestParam("id") Long id, Model model, HttpServletRequest request) {
								
		Optional<Activity> activityOptional = activityService.findActivityById(id);
		
		if (activityOptional.isPresent()) {
			activityService.deleteActivity(activityOptional.get().getId());
		}
		
		return "redirect:/homeSegreteria";
	}
	
	
	@RequestMapping(path = "/updateTertulia", method = RequestMethod.GET )
	public String updateShowFormTertuliaATema (@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		return "redirect:/homeSegreteria?id="+id+"#programmazioneTertulieATema";
	}
	
	
	@RequestMapping(path = "/updateTertulia", method = RequestMethod.POST )
	public String updateTertuliaATema (@ModelAttribute Activity activity, @RequestParam("id") Long id, Model model, HttpServletRequest request) {
		activityService.updateActivity(id, activity.getStartDate(), activity.getStartTime(), activity.getEndTime());
		return "redirect:/homeSegreteria";
	}
	
}
