package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;

@Component
public class MultipartProcessingUtility {
	
	// All Services required
	private final ActivityService activityService;
	
	private static String ROOT_UPLOAD_DIR = "/tmp/activity-photos/";
	
	@Autowired
	public MultipartProcessingUtility(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	public String multipartProcessing(MultipartFile multipartFile, Activity activity) throws IOException {
		
		// Generation of filename
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
               
        
        // Insertion of filename in Acticity entity
        activity.setPhoto(fileName);
    
    
    	// Saving Activity in DB
    	String err = activityService.addNewActivity(activity);
    	
    	if (err != null) {
    		
        	System.out.println(err);
        	
        	return err;
    	}
    	
    	// Checks if ROOT_UPLOAD_DIR exist
    	if (!Files.exists(Paths.get(ROOT_UPLOAD_DIR))) {
    		Files.createDirectory(Paths.get(ROOT_UPLOAD_DIR));
    	}

        // Generation of path to the directory where to store the photo 
        String uploadDir = ROOT_UPLOAD_DIR + activity.getId();
        
        Path uploadPath = Paths.get(uploadDir);
        System.out.println(uploadPath.getFileName());
        System.out.println(uploadPath.toString());
        
        // Checks if folder exist
        if (!Files.exists(uploadPath)) {
        	Files.createDirectory(uploadPath);
        }
        
        // Saving file in the correct dir
        try (InputStream inputStream = multipartFile.getInputStream()) {
        	
        	Path filePath = uploadPath.resolve(fileName);
        	
        	Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		
        } catch (IOException ioe) {
			throw new IOException("Could not save the file!");
		}
        
        return null;
		
	}

}
