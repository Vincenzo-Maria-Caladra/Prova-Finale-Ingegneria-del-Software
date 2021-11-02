package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

/**
 * @author VectorCode
 *
 */
@Controller
@RequestMapping("/homeDirettore")
public class HomeDirettoreController {
	
	// All Services required
	private final UserService userService;
	
	@Autowired
	public HomeDirettoreController(UserService userService) {
		this.userService = userService;
	}
	
	//Initialization of homeDirettore page
	@GetMapping
	public String goToHomeDirettore(Model model) {
		
		//Add list attributes to the page
		model.addAttribute("listOfStudenti", userService.getAllAppUserStudenti());
		model.addAttribute("listOfTutor", userService.getAllAppUserTutor());
		
		return "homeDirettore";
	}
	
	//Delete User handler
	@RequestMapping(path = "/deleteUser" , method = RequestMethod.POST)
	public String deleteStudente (@RequestParam("userId") Long userId, Model model) {
		
		userService.removeUser(userId);
		
		return "redirect:/homeDirettore";
	}
	
	//Update users Tutor handler
	@RequestMapping(path = "/updateTutor", method = RequestMethod.POST)
	public String updateTutorStudente (@RequestParam("userId") Long userId, @RequestParam("tutor") String tutorId, Model model) {
		
		userService.updateUserTutor(userId, tutorId);
		
		return "redirect:/homeDirettore";
	}
	

	
}
