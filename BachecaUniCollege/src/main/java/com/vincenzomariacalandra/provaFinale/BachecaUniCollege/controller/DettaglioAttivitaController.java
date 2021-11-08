package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;

/**
 * @author VectorCode
 *
 */
@Controller
@RequestMapping("/dettaglioAttivita")
public class DettaglioAttivitaController {
	
	//Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	
	@Autowired
	public DettaglioAttivitaController(ActivityService activityService, UserActivityService userActivityService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
	}
	
	//Page initialization
	@GetMapping
	public String getDettaglioAttivita(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		//Retrieve usefull information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser user = ((AppUser)principal);
		
		Optional<Activity> activityOptional = activityService.findActivityById(id);
		Optional<UserActivity> userOrganizerOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user.getEmail(), id, true);
		Optional<UserActivity> userActivityOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user.getEmail(), id, false);
		
		
		//Checks if  the logged AppUser is the organizer of the Activity
		//Set a boolean model attribute "organizer"
		if(userOrganizerOptional.isPresent()) {
			
			if(userOrganizerOptional.get().isOrganizer()) {
				model.addAttribute("msg1", "Sei l'organizzatore");
				model.addAttribute("organizer", Boolean.TRUE);
			} else {
				model.addAttribute("organizer", Boolean.FALSE);
			}
		} else {
			model.addAttribute("organizer", Boolean.FALSE);
		}
		
		//Added other usefull model attributes to the page
		if (activityOptional.isPresent()) {
			
			//to get activity information
			model.addAttribute("activity", activityOptional.get());
			
			//to get list of partecipats
			ArrayList<UserActivity> list = new ArrayList<>();
			userActivityService.listAllUserOfOneActivity(activityOptional.get()).iterator().forEachRemaining(list::add);
			model.addAttribute("userOfActivities", list);
			
			//Checks if the logged AppUser is subcribe to the actvitity
			//This will be use to show the subscribe and unsubscribe buttons 
			if(userActivityOptional.isPresent()) {
				model.addAttribute("iscritto", Boolean.TRUE);
				model.addAttribute("msg", "Sei iscritto a questa attivit�!");
			} else {
				model.addAttribute("iscritto", Boolean.FALSE);
				model.addAttribute("msg", "Non sei iscritto a questa attivit�!");
			}
			
		}
		
		return "dettaglioAttivita";
	}
	
	//Activity Subscription handler
	@RequestMapping(path = "/subscribe", method = RequestMethod.POST)
	public String activitySubscription (@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		//Retrive usefull information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser user = ((AppUser)principal);
		
		Optional<Activity> activityOptional = activityService.findActivityById(id);
		Optional<UserActivity> userActivityOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user.getEmail(), id, false);
		
		//Check if userActivity entity is present
		if (userActivityOptional.isPresent()) {
			
			return "redirect:/dettaglioAttivita?id="+id;
		}
		
		//Check if activity is present
		if (activityOptional.isPresent()) {
			
			userActivityService.insertNewUserActivity(user.getId(), activityOptional.get().getId(), false);
		}
		
		return "redirect:/dettaglioAttivita?id="+id;
	}
	
	//Activity unsubscription handler
	@RequestMapping(path = "/unsubscribe", method = RequestMethod.POST )
	public String activityUnSubscription(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		//Retrive usefull information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser user = ((AppUser)principal);
		
		Optional<UserActivity> userActivityOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user.getEmail(), id, false);
		
		//Check if userActivity is present
		if (userActivityOptional.isPresent()) {
			
			//Delete subscrition
			userActivityService.deleteUserActivityByActivityId(userActivityOptional.get().getUser(), userActivityOptional.get().getActivity());
			
		}
		
		return "redirect:/dettaglioAttivita?id="+id;
	}
	
	//Delete Activity handler
	@RequestMapping(path = "/deleteActivity", method = RequestMethod.POST )
	public String activityDelete(@RequestParam("id") Long id) {
		
		String err = activityService.deleteActivity(id);
		
		return "redirect:/homePage";
	}
	
}
