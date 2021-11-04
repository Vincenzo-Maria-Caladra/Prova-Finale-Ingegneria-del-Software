package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.RegistrationRequest;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.RegistrationService;

/**
 * @author VectorCode
 *
 */
@Controller
@SessionAttributes("registrationRequest")
public class RegistrationController {
	
	// All Services required
	private final RegistrationService registrationService;
	
	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		super();
		this.registrationService = registrationService;
	}
	
	// Registration page initialization
	@RequestMapping(path = "/registration", method = RequestMethod.GET)
	public String registrationPage(Model model) {
		model.addAttribute("registrationRequest", new RegistrationRequest());
		return "registration";
	}
	
	// Registration handler
	@RequestMapping(path = "/registration", method = RequestMethod.POST)
	public String register (@ModelAttribute RegistrationRequest registrationRequest, Model model) {
		registrationService.register(registrationRequest);
		model.addAttribute("msg","Thanks to have sended the registration, an email has been sended to your email" ); 
		return "confirmationPage";
	}
	
	// Confirm Registration handler
	@RequestMapping(path = "/registration/confirm", method = RequestMethod.GET)
    public String confirm(@RequestParam("token") String token, Model model) {
		registrationService.confirmToken(token);
		model.addAttribute("msg","Thanks to have confirmed the registration, now you could access to the portal" ); 
		return "confirmationPage";
    }
	
}
