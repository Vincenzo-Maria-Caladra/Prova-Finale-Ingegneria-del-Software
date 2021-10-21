package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/gestioneAttivita")
public class GestioneAttivitaController {
	
	
	// All Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public GestioneAttivitaController(ActivityService activityService, UserActivityService userActivityService, UserService userService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	//Inizializzazione della pagina dettaglioAttvita.html
	@GetMapping
	public String getDettaglioAttivita(Model model, HttpServletRequest request) {
		
		//Retrive usefull information
		ArrayList<Activity> list  = new ArrayList<>();
		activityService.getActivitiesToApprove().iterator().forEachRemaining(list::add);
		model.addAttribute("activities", list);
		
		return "gestioneAttivita";
	}
	
	@RequestMapping(path = "/acceptActivity", method = RequestMethod.POST)
	public String acceptActivity (@RequestParam("id") Long id, Model model, HttpServletRequest request) {
		
		activityService.updateActivityState(id);
		
		return "redirect:/gestioneAttivita";
	}
	
	@RequestMapping(path = "/deleteActivity", method = RequestMethod.POST )
	public String deleteActivity (@RequestParam("id") Long id, Model model, HttpServletRequest request) {
								
		Optional<Activity> activityOptional = activityService.findActivityById(id);
		
		if (activityOptional.isPresent()) {
			activityService.deleteActivity(activityOptional.get().getId());
		}
		
		return "redirect:/gestioneAttivita";
	}
	
	
}
