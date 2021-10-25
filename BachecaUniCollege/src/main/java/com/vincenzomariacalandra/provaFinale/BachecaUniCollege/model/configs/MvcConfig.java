package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.configs;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class MvcConfig implements WebMvcConfigurer {
 
	// Configuration to expose filesystem directory where to store the imgs of the activity
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	registry.addResourceHandler("/activity-photos/**") //reference dir name
    				.addResourceLocations("file:/C:\\activity-photos/"); //real path dir reference

    }    
    
}