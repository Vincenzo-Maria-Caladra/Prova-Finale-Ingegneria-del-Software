package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public SecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers("/registration/**", "/login/**", "/error/**", "/", "/confirmationPage/**").permitAll()
				.antMatchers("/homeDirettore/**").hasAuthority("DIRETTORE")
				.antMatchers("/gestioneAttivita/**").hasAuthority("DIRETTORE")
				.antMatchers("/homeTutor/**").hasAuthority("TUTOR")
				.antMatchers("/homeSegreteria/**").hasAuthority("SEGRETERIA")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/homePage", true)
				.failureUrl("/login?error=true")
				.and().logout()
				.logoutSuccessUrl("/")
				.logoutUrl("/perform_logout")
				.invalidateHttpSession(true);
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(userService);

		return provider;
	}

}
