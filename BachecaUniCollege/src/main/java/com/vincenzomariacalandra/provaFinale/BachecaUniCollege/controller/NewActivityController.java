package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;

/**
 * @author VectorCode
 *
 */
@Controller
public class NewActivityController {
	
	// All Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	
	@Autowired
	public NewActivityController(ActivityService activityService, UserActivityService userActivityService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
	}
	
	// Initialization newActivity page
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.GET)
	public String creaNuovaAttivitaPage (Model model, HttpServletRequest request) {
		
		if (!model.containsAttribute("activity")) {
			model.addAttribute("activity", new Activity());
		}
		
		return "creaAttivita";
	}
	
	//Nuova Attività  handler
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.POST)
	public String addActivity (@Valid @ModelAttribute("activity") Activity activity, @RequestParam(name = "fileImage", required = false) MultipartFile multipartFile, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes)
	throws IOException {
		
		
		if (multipartFile != null) {
			
			//Error checking business constraints
			String err = multipartProcessing(multipartFile, activity);
			
        	if (err != null) {
        		
        		//Adding error to the model
        		redirectAttributes.addFlashAttribute("error", err);	
        		
        		//Redirect to correct pane of creaAttivita.html
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
        		
        		//Redirect to correct pane of creaAttivita.html
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
		userActivityService.insertNewUserActivity(user.getId(), activity.getId(), true);
		
		redirectAttributes.addFlashAttribute("msg", "Attività aggiunta con successo! Attendi che venga approvata!");	
		
		return "redirect:/homePage";
	}

	private String multipartProcessing(MultipartFile multipartFile, Activity activity) throws IOException {
		
		// Generation of filename
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
               
        
        // Insertion of filename in Acticity entity
        activity.setPhoto(fileName);
    
    
    	// Saving Activity in DB
    	String err = activityService.addNewActivity(activity);
    	
    	if (err != null) {
    		
        	System.out.println(err);
        	
        	return err;
    	}

        // Generation of path to the directory where to store the photo 
        String uploadDir = "/activity-photos/" + activity.getId();
        Path uploadPath = Paths.get(uploadDir);
        
        
        // Checks if folder exist
        if (!Files.exists(uploadPath)) {
        	Files.createDirectory(uploadPath);
        }
        
        // Saving file in the correct dir
        try (InputStream inputStream = multipartFile.getInputStream()) {
        	
        	Path filePath = uploadPath.resolve(fileName);
        	
        	Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		
        } catch (IOException ioe) {
			throw new IOException("Could not save the file!");
		}
        
        return null;
		
	}
}
