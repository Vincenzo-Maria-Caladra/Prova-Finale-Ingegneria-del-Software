package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

/**
 * @author VectorCode
 *
 */
@Controller
public class NewActivityController {
	
	// All Services required
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public NewActivityController(ActivityService activityService, UserActivityService userActivityService, UserService userService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	// Initialization newActivity page
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.GET)
	public String creaNuovaAttivitaPage (Model model) {
		model.addAttribute("newActivity", new Activity());
		return "creaAttivita";
	}
	
	//Nuova Attività  handler
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.POST)
	public String addActivity (@ModelAttribute Activity activity, @RequestParam(name = "fileImage", required = false) MultipartFile multipartFile, Model model, HttpServletRequest request)
	throws IOException {
		
		if (multipartFile != null) {
			multipartProcessing(multipartFile, activity);
        } else {
        	activityService.addNewActivity(activity);
        }
        
		//Retrieve usefull information
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AppUser user = ((AppUser)principal);
		
		//Insert userActivity as "organizer"
		userActivityService.insertNewUserActivity(user.getId(), activity.getId(), true);
		
		model.addAttribute("msg", "Attivit� aggiunta con successo! Attendi che venga approvata!");
		
		
		return "redirect:/homePage";
	}

	private void multipartProcessing(MultipartFile multipartFile, Activity activity) throws IOException {
		
		// Generation of filename
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
               
        
        // Insertion of filename in Acticity entity
        activity.setPhoto(fileName);
    
    
    	// Saving Activity in DB
    	String err = activityService.addNewActivity(activity);

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
		
	}
}
