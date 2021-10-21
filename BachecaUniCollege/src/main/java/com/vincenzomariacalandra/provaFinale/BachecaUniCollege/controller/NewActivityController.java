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

/**
 * @author CalandraVM
 * Classe Controller per la pagina newActivity.html
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
	
	// Inizzializzazione della pagina newActivity.html
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.GET)
	public String creaNuovaAttivitaPage (Model model) {
		model.addAttribute("newActivity", new Activity());
		return "creaAttivita";
	}
	
	//  Gestione dell'inserimento di una nuova attivit� tramite form
	@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.POST)
	public String addActivity (@ModelAttribute Activity activity, @RequestParam("fileImage") MultipartFile multipartFile, Model model, HttpServletRequest request)
	throws IOException {
		
		// Generazione del filename della foto caricata 
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
               
        
        // Inserimento del filename della foto nell'istanza dell'Activity
        // generata via form
        activity.setPhoto(fileName);
        
        
        // Salvataggio dell'activity nel DB
        Activity savedActivity = activityService.addNewActivity(activity);
 
        
        // Generazione del path alla directory in cui inserire la nuova foto caricata 
        String uploadDir = "/activity-photos/" + savedActivity.getId();
        Path uploadPath = Paths.get(uploadDir);
        
        
        // Verifica dell'esistenza della cartella
        if (!Files.exists(uploadPath)) {
        	Files.createDirectory(uploadPath);
        }
        
        // Scrittura del file nell'apposita directory
        try (InputStream inputStream = multipartFile.getInputStream()) {
        	
        	Path filePath = uploadPath.resolve(fileName);
        	
        	Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		
        } catch (IOException ioe) {
			throw new IOException("Could not save the file!");
		}
        
        
        // Inserimento del creatore dell'attivit� nella tabella userActivity come "organizer"
		String user = request.getUserPrincipal().getName();
		Optional<AppUser> optionalUser = userService.getUser(user);
		userActivityService.insertNewUserActivity(optionalUser.get().getId(), activity.getId(), true);
		
		model.addAttribute("msg", "Attivit� aggiunta con successo! Attendi che venga approvata!");
		
		
		return "redirect:/homePage";
	}
}
