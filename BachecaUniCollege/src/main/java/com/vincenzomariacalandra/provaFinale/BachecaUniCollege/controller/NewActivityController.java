package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;

@Controller
public class NewActivityController {
	
	private final ActivityService activityService;
	private final UserActivityService userActivityService;
	private final UserService userService;
	
	@Autowired
	public NewActivityController(ActivityService activityService, UserActivityService userActivityService, UserService userService) {
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userService = userService;
	}
	
	//@DeleteMapping(path = "/{activityId}")
	public void deleteActivity(@PathVariable("activityId") long activityId) {
		activityService.deleteActivity(activityId);
	}
	
	//@PutMapping(path = "/{activityId}")
	public Activity updateActivity(@PathVariable("activityId") long activityId,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) boolean state,
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) ActivityType activityType,
			@RequestParam(required = false) ActivityCredits activityCredits) {
		
		return activityService.updateActivity(activityId, title, state, startDate, endDate,
				startTime,endTime, activityType, activityCredits);
	}
	
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.GET)
	public String creaNuovaAttivitaPage (Model model) {
		model.addAttribute("newActivity", new Activity());
		return "creaAttivita";
	}
	
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.POST)
	public String addActivity (@ModelAttribute Activity activity, @RequestParam("fileImage") MultipartFile multipartFile, Model model, HttpServletRequest request)
	throws IOException {
		
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        
        System.out.println(fileName+": " + fileName);
        
        activity.setPhoto(fileName);
        
        Activity savedActivity = activityService.addNewActivity(activity);
 
        String uploadDir = "/activity-photos/" + savedActivity.getId();
 
        Path uploadPath = Paths.get(uploadDir);
        
        System.out.println(uploadPath.toFile().getPath());
        
        if (!Files.exists(uploadPath)) {
        	Files.createDirectory(uploadPath);
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
        	
        	Path filePath = uploadPath.resolve(fileName);
        	
        	Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		
        } catch (IOException ioe) {
			throw new IOException("Could not save the file!");
		}
        
        
		String user = request.getUserPrincipal().getName();
		
		Optional<AppUser> optionalUser = userService.getUser(user);
		
		userActivityService.insertNewUserActivity(optionalUser.get().getId(), activity.getId(), true);
		
		model.addAttribute("msg", "Attività  aggiunta con successo! Attendi che venga approvata!");
		
		ArrayList<Activity> list = new ArrayList<>();
		
		activityService.getActivities().iterator().forEachRemaining(list::add);
		
		model.addAttribute("activities", list);
		
		return "redirect:/homePage";
	}
}
