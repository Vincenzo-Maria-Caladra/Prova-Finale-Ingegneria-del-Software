package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

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
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

/**
 * @author VectorCode
 *
 */
@Controller
@RequestMapping("/homeSegreteria")
public class HomeSegreteria {

	// All Services required
	private final ActivityService activityService;
	private final UserService userService;

	@Autowired
	public HomeSegreteria(ActivityService activityService, UserService userService) {
		this.activityService = activityService;
		this.userService = userService;
	}

	// Initialization of homeSegreteria page
	@GetMapping
	public String getHomeSegreteria(@RequestParam(name = "id", required = false) Long id, Model model) {

		// Add list attributes
		model.addAttribute("userCreditsList", userService.getAllUsersCredits());
		model.addAttribute("tertulieList", activityService.getAllTertulieToBeApproved());

		// Show a form to modify the activity selected
		if (id != null) {
			model.addAttribute("form", Boolean.TRUE);
			model.addAttribute("updatedActivity", new Activity());
		} else {
			model.addAttribute("form", Boolean.FALSE);

		}

		return "homeSegreteria";
	}

	// Accept activity handler
	@RequestMapping(path = "/acceptTertulia", method = RequestMethod.POST)
	public String acceptTertuliaATema(@RequestParam("id") Long id, Model model, HttpServletRequest request) {

		activityService.updateActivityState(id);

		return "redirect:/homeSegreteria";
	}

	// Reject activity handler
	@RequestMapping(path = "/deleteTertulia", method = RequestMethod.POST)
	public String deleteTertuliaATema(@RequestParam("id") Long id, Model model, HttpServletRequest request) {

		activityService.deleteActivity(id);
		
		return "redirect:/homeSegreteria";
	}
	
	// Update Activity handler
	@RequestMapping(path = "/updateTertulia", method = RequestMethod.GET)
	public String updateShowFormTertuliaATema(@RequestParam("id") Long id, Model model, HttpServletRequest request) {

		return "redirect:/homeSegreteria?id=" + id + "#programmazioneTertulieATema";
	}
	
	// Update activity post handler
	@RequestMapping(path = "/updateTertulia", method = RequestMethod.POST)
	public String updateTertuliaATema(@ModelAttribute Activity activity, @RequestParam("id") Long id, Model model,
			HttpServletRequest request) {
		
		String err = activityService.updateActivity(id, activity.getStartDate(), activity.getStartTime(), activity.getEndTime());
		
		return "redirect:/homeSegreteria";
	}

}
