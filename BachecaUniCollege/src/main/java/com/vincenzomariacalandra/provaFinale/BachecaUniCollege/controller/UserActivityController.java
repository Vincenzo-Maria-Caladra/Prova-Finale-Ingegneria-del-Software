package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;

@RestController
@RequestMapping(path = "api/v1/userActivity")
public class UserActivityController {
	
	private final UserActivityService userActivityService;
	
	@Autowired
	public UserActivityController(UserActivityService userActivityService) {
		super();
		this.userActivityService = userActivityService;
	}
	
	@GetMapping
	public List<UserActivity> listAllActivities () {
		return userActivityService.listAllUserActivities();
	}
	
	@PostMapping
	public UserActivity insertNewUserActivity (@RequestParam long idUser, @RequestParam long idActivity, @RequestParam  boolean organizer) {
		return userActivityService.insertNewUserActivity(idUser, idActivity, organizer);
	}
	
	@DeleteMapping("/{idUserActivity}")
	public UserActivity deleteUserActivity (@PathVariable("idUserActivity") long idUserActivity) {
		return userActivityService.deleteUserActivityById(idUserActivity);
	}
	
}
