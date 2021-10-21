package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author CalandraVM
 * Classe Controller per la pagina login.html
 */
@Controller
public class LoginController {
	
	  // Login form
	  @RequestMapping("/login")
	  public String login() {
	    return "login";
	  }
}
