package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@SpringBootApplication
public class BachecaUniCollegeApplication implements CommandLineRunner {

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(BachecaUniCollegeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Creation of
		// Default Super User 
		
		String name = "UniCollege";
		String surname = "Bacheca";
		String email = "residenza.elis.bacheca@gmail.com";
		String passString = bCryptPasswordEncoder.encode("elis1928");
		UserType userType = UserType.DIRETTORE;
		
		
		AppUser appUser = new AppUser(name, surname, email, passString, userType);
		appUser.setEnabled(true);
		
		if (userRepository.findByEmail(email).isEmpty()) {
			userRepository.save(appUser);
		}
		
	}
	
	

}
