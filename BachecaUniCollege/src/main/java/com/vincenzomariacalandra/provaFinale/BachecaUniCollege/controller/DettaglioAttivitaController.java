package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

@Controller
@RequestMapping("/dettaglioAttivita")
public class DettaglioAttivitaController {
	
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public DettaglioAttivitaController(ActivityService activityService, UserActivityService userActivityService, UserService userService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	@GetMapping
	public String getDettaglioAttivita(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		Optional<Activity> activityOptional = activityService.findActivityById(id);
		
		String user = request.getUserPrincipal().getName();
		
		Optional<UserActivity> userOrganizerOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user, id, true);
		Optional<UserActivity> userActivityOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user, id, false);

		
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
		
		
		if (activityOptional.isPresent()) {
			
			model.addAttribute("activity", activityOptional.get());
			
			ArrayList<UserActivity> list = new ArrayList<>();
			
			userActivityService.listAllUserOfOneActivity(activityOptional.get()).iterator().forEachRemaining(list::add);
			
			if(userActivityOptional.isPresent()) {
				model.addAttribute("iscritto", Boolean.TRUE);
				model.addAttribute("msg", "Sei iscritto a questa attività!");
			} else {
				model.addAttribute("iscritto", Boolean.FALSE);
				model.addAttribute("msg", "Non sei iscritto a questa attività!");
			}
			
			model.addAttribute("userOfActivities", list);
			
		}
		
		return "dettaglioAttivita";
	}
	
	@RequestMapping(path = "/subscribe", method = RequestMethod.POST)
	public String activitySubscription (@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		String user = request.getUserPrincipal().getName();

		Optional<Activity> activityOptional = activityService.findActivityById(id);
		
		Optional<AppUser> userOptional = userService.getUser(user);
		
		Optional<UserActivity> userActivityOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user, id, false);
		
		if (userActivityOptional.isPresent()) {
			
			model.addAttribute("activity", activityOptional.get());
			
			ArrayList<UserActivity> list = new ArrayList<>();
			
			userActivityService.listAllUserOfOneActivity(activityOptional.get()).iterator().forEachRemaining(list::add);
			
			model.addAttribute("userOfActivities", list);
			
			model.addAttribute("iscritto", Boolean.TRUE);
			
			model.addAttribute("msg", "Sei già iscritto all'attività!");
			
			return "redirect:/dettaglioAttivita?id="+id;
		}
				
		if (activityOptional.isPresent() && userOptional.isPresent()) {
			
			userActivityService.insertNewUserActivity(userOptional.get().getId(), activityOptional.get().getId(), false);
			
			model.addAttribute("activity", activityOptional.get());
			
			ArrayList<UserActivity> list = new ArrayList<>();
			
			userActivityService.listAllUserOfOneActivity(activityOptional.get()).iterator().forEachRemaining(list::add);
			
			model.addAttribute("iscritto", Boolean.TRUE);
			
			model.addAttribute("userOfActivities", list);
			
		}
		
		return "redirect:/dettaglioAttivita?id="+id;
	}
	
	@RequestMapping(path = "/unsubscribe", method = RequestMethod.POST )
	public String activityUnSubscription(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		String user = request.getUserPrincipal().getName();
		
		Optional<UserActivity> userActivityOptional = userActivityService.getUserActivityByUserAndActivityAndOrganizer(user, id, false);
				
		if (userActivityOptional.isPresent()) {
			
			userActivityService.deleteUserActivityByActivityId(userActivityOptional.get().getUser(), userActivityOptional.get().getActivity());
			
			model.addAttribute("activity", userActivityOptional.get().getActivity());
			
			ArrayList<UserActivity> list = new ArrayList<>();
			
			userActivityService.listAllUserOfOneActivity(userActivityOptional.get().getActivity()).iterator().forEachRemaining(list::add);
			
			model.addAttribute("userOfActivities", list);
			
			model.addAttribute("iscritto", Boolean.FALSE);
			
		}
		
		return "redirect:/dettaglioAttivita?id="+id;
	}
	
	
	@RequestMapping(path = "/deleteActivity", method = RequestMethod.POST )
	public String activityDelete(@RequestParam("id") Long id) {
		
		activityService.deleteActivity(id);
		
		return "redirect:/homePage";
	}
	
}
