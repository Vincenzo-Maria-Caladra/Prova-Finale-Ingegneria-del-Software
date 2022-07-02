package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;

/**
 * @author VectorCode
 *
 */
@Controller
@RequestMapping("/gestioneAttivita")
public class GestioneAttivitaController {

	// All Services required
	private final ActivityService activityService;

	@Autowired
	public GestioneAttivitaController(ActivityService activityService) {
		this.activityService = activityService;

	}

	// Initialization dettaglioAttvita.html page
	@GetMapping
	public String getDettaglioAttivita(Model model, HttpServletRequest request) {

		//Retrieve useful information
		ArrayList<Activity> list = new ArrayList<>();
		activityService.getActivitiesToApprove().iterator().forEachRemaining(list::add);
		model.addAttribute("activities", list);

		return "gestioneAttivita";
	}

	// Accept Activity handler
	@RequestMapping(path = "/acceptActivity", method = RequestMethod.POST)
	public String acceptActivity(@RequestParam("id") Long id, Model model, HttpServletRequest request, RedirectAttributes attributes) {

		String err = activityService.updateActivityState(id);

		if (err != null) {
			attributes.addFlashAttribute("err", err);
		}
		
		return "redirect:/gestioneAttivita";
	}

	// Delete Actvity handler
	@RequestMapping(path = "/deleteActivity", method = RequestMethod.POST)
	public String deleteActivity(@RequestParam("id") Long id, Model model, HttpServletRequest request, RedirectAttributes attributes) {

		String err = activityService.deleteActivity(id);
		
		if (err != null) {
			attributes.addFlashAttribute("err", err);
		}
		
		return "redirect:/gestioneAttivita";
	}

}
