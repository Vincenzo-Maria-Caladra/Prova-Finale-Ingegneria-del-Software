package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.EmailSender;


/**
 * @author VectorCode
 *
 */
@Service
public class ActivityService {

	//List all repository to use
	private final ActivityRepository activityRepository;
	private final EmailSender emailSender;
	private final UserService userService;
	
	@Autowired
	public ActivityService(ActivityRepository activityRepository, EmailSender emailSender, UserService userService) {
		this.activityRepository = activityRepository;
		this.emailSender = emailSender;
		this.userService = userService;
	}
	
	//Return an activity
	public Optional<Activity> findActivityById(Long id) {
		
		if (activityRepository.findById(id).isEmpty()) {
			throw new NullPointerException("Activity not found" + id);
		}
		
		return activityRepository.findById(id);
	}

	//Add a new activity
	public String addNewActivity(Activity activity) {
		
		// Check for errors
		String err = activityValidator(activity);
		
		if (err != null) {
			
			return err;
			
		} else {
			
			activity.setState(false);
			activityRepository.save(activity);
		}
		
		return null;
	}
	
	//Activity validation functions
	private String activityValidator(Activity activity) {
		
		if (activity == null) {
			return "Invalid activity!";
		}
		else if (activity.getTitle() == null || activity.getTitle().equals("")) {
			return "Title should not be empty!";
		} else if (activity.getDescrizione() == null || activity.getDescrizione().equals("") ) {
			return "Description should not be empty!";
		} else if (activity.getStartDate().after(activity.getEndDate())) {
			return "Start date should not be after end date!";
		} else if (activity.getStartTime().after(activity.getEndTime())) {
			return "Start time should not be after end time!";
		}
		
		return null;
	}

	//Delete activity function
	public String deleteActivity(long id) {
		
		
		Optional<Activity> activityOptional =
				activityRepository.findById(id);
		
		if (activityOptional.isPresent()) {
			activityRepository.deleteById(id);
			return null;
			
		} else {
			
			return "Activity does not exist!";
			
		}
		
	}
	
	//Update activity function
	@Transactional
	public String updateActivity(long activityId, Date startDate, Time startTime, Time endTime) {
				
		Activity activity = activityRepository.findById(activityId).orElseThrow( () -> new IllegalStateException(
				"Activity with id:" + activityId + " not found!"));
		
		if(startDate.before(Date.valueOf(LocalDate.now()))) {
			
			return "Data inserita antecedente ad oggi!";
			
		} else {
			
			activity.setStartDate(startDate);
			activity.setStartTime(startTime.toString());
			
			if (activityValidator(activity) != null) {
				return activityValidator(activity);
			}
			
		}
		
		return null;
	}
	
	// Update activity state function
	@Transactional
	public String updateActivityState (Long id) {
		
		Optional<Activity> activityOptional = activityRepository.findById(id);
		
		if(activityOptional.isPresent()) {
			
			activityOptional.get().setState(true);
				
				for(AppUser user : userService.getUsers()) {
					
					emailSender.send(user.getEmail(), buildEmail(user.getName(), activityOptional.get()));
					
				}
	
			
			return null;
		}
		
		return "Activity not found!";
		
	}
	
	// Return a list activities to be approved, without book
	public List<Activity> getActivitiesToApprove(){
		
		return activityRepository.findByStateAndActivityTypeNot(false, ActivityType.LIBRO);
	}
	
	// Return a list activities approved, without book
	public List<Activity> getActivitiesApproved(){
		
		return activityRepository.findByStateAndActivityTypeNot(true, ActivityType.LIBRO);
	}
	
	// Return a list of tertulie a tema to be approved
	public List<Activity> getAllTertulieToBeApproved() {
		
		return activityRepository.findAllByActivityTypeAndState(ActivityType.TERTULIA_A_TEMA, Boolean.FALSE);
	}
	
	
    // template for the notification  email
    private String buildEmail(String name, Activity activity) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">New Activity!</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">A new activity has been added to the bulletin board!" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Title:" + activity.getTitle() +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Credits:" + activity.getActivityCredits() +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Descrizione:" + activity.getDescrizione() +
                "			 <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
