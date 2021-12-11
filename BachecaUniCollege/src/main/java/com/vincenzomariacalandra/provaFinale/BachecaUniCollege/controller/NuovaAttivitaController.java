package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.MultipartProcessingUtility;

/**
 * @author VectorCode
 *
 */
@Controller
public class NuovaAttivitaController {
	
	// All Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final MultipartProcessingUtility processing;
	
	@Autowired
	public NuovaAttivitaController(ActivityService activityService, UserActivityService userActivityService, MultipartProcessingUtility multipartProcessing) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.processing = multipartProcessing;
	}
	
	// Initialization newActivity page
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.GET)
	public String creaNuovaAttivitaPage (Model model, HttpServletRequest request) {
		
		if (!model.containsAttribute("activity")) {
			model.addAttribute("activity", new Activity());
		}
		
		if (!model.containsAttribute("books")) {
			model.addAttribute("books", activityService.getAllBooks());
		}
		
		return "nuovaAttivita";
	}
	
	//Nuova Attività  handler
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.POST)
	public String addActivity (@Valid @ModelAttribute("activity") Activity activity, @RequestParam(name = "fileImage", required = false) MultipartFile multipartFile, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes)
	throws IOException {
		
		
		if (multipartFile != null) {
			
			//Error checking business constraints
			String err = processing.multipartProcessing(multipartFile, activity);
			
        	if (err != null) {
        		
        		//Adding error to the model
        		redirectAttributes.addFlashAttribute("error", err);	
        		
        		//Redirect to correct pane of nuovaAttivita.html
        		if (activity.getActivityType() == ActivityType.TERTULIA_A_TEMA) {
        			redirectAttributes.addFlashAttribute("panel", "TERTULIA_A_TEMA");
        		} else {
        			redirectAttributes.addFlashAttribute("panel", "ATTIVITA_GENERICA");
        		}
        		
        		return "redirect:/nuovaAttivita";     		
        		
        	}
        	
    
        } else {
        	
        	// Activity type Libro
        	// Error checking business constraints 
        	String err = activityService.addNewActivity(activity);
        	
        	if (err != null) {
        		
        		//Redirect to correct pane of nuovaAttivita.html
        		//Adding error to the model
        		redirectAttributes.addFlashAttribute("error", err);	
        		redirectAttributes.addFlashAttribute("panel", "LIBRO");	
        		
        		return "redirect:/nuovaAttivita";     		
        		
        	}
        	
        	
        }
        
		//Retrieve usefull information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser user = ((AppUser)principal);
		
		//Insert userActivity as "organizer"
		String err = userActivityService.insertNewUserActivity(user.getId(), activity.getId(), true);
		
		// Check for errors
		if (err != null) {
			
    		//Redirect homeBacheca.html
    		//Adding error to the model
    		redirectAttributes.addFlashAttribute("error", err);	
    		return "redirect:/homeBacheca"; 	
			
		}
		
		// Adding default success message
		redirectAttributes.addFlashAttribute("msg", "Attività aggiunta con successo! Attendi che venga approvata!");	
		
		return "redirect:/homeBacheca";
	}
	
	@RequestMapping(path = "/nuovaAttivita/libro", method = RequestMethod.POST)
	public String chooseJustApprovedBook (@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
		
		//Retrieve usefull information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser user = ((AppUser)principal);
		
		String err = userActivityService.addAJustRedBook(id, user);
		
    	if (err != null) {
    		
    		//Redirect to correct pane of nuovaAttivita.html
    		//Adding error to the model
    		redirectAttributes.addFlashAttribute("error", err);	
    		redirectAttributes.addFlashAttribute("panel", "LIBRO");	
    		
    		return "redirect:/nuovaAttivita";     		
    		
    	}
		
    	// Adding default success message
		redirectAttributes.addFlashAttribute("msg", "Attività aggiunta con successo! Attendi che venga approvata!");	
		
		return "redirect:/homeBacheca";
	}
}
