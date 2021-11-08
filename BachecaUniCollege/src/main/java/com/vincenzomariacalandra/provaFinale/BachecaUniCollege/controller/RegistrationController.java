package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.pojo.RegistrationRequest;
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
		
		if (!model.containsAttribute("registrationRequest")) {
			model.addAttribute("registrationRequest", new RegistrationRequest());
		}
		
		return "registration";
	}
	
	// Registration handler
	@RequestMapping(path = "/registration", method = RequestMethod.POST)
	public String register (@ModelAttribute RegistrationRequest registrationRequest, Model model, RedirectAttributes redirectAttributes) {
		
		String err = registrationService.register(registrationRequest);
		
		if (err != null) {
			
			//Adding error to the model
    		redirectAttributes.addFlashAttribute("error", err);	
    		
			return "redirect:/registration";
		}
		
		//Adding msg to the model
		redirectAttributes.addFlashAttribute("msg", "Registrazione avvenuta con successo! \n Per proseguire conferma la email.");	
		
		return "redirect:/confirmationPage";
	}
	
	// Confirm Registration handler
	@RequestMapping(path = "/registration/confirm", method = RequestMethod.GET)
    public String confirm(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {
		
		String err = registrationService.confirmToken(token);
		
		if (err != null) {
			
			//Adding error to the model
			model.addAttribute("errorMessage", err);
    		
    		return "error";
    		
		}
		
		//Adding msg to the model
		model.addAttribute("msg","Thanks to have confirmed the registration, now you could access to the portal" ); 
		
		return "confirmationPage";
    }
	
}
