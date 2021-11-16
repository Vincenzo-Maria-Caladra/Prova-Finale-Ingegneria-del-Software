package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.EmailSender;

/**
 * @author VectorCode
 *
 */
@Service
public class ActivityService {

	// List all repository to use
	private final ActivityRepository activityRepository;
	private final EmailSender emailSender;
	private final UserService userService;

	public ActivityService(ActivityRepository activityRepository, EmailSender emailSender, UserService userService) {
		super();
		this.activityRepository = activityRepository;
		this.emailSender = emailSender;
		this.userService = userService;
	}

	// Return an activity
	public Optional<Activity> findActivityById(Long id) {

		if (id == null) {
			return null;
		}

		return activityRepository.findById(id);
	}

	// Add a new activity
	public String addNewActivity(Activity activity) {

		// Check for errors
		String err = activityValidator(activity);

		if (err != null) {

			return err;

		} else {
			// Perform data formatting and logic operation before save entity
			activity.setState(false);
			activity.setTitle(activity.getTitle().trim());
			activity.setDescrizione(activity.getDescrizione().trim());
			activityRepository.save(activity);
		}

		return null;
	}

	// Activity validation functions
	private String activityValidator(Activity activity) {

		if (activity == null) {
			return "Invalid activity!";
		} else if (activity.getTitle() == null || activity.getTitle().trim().equals("")) {
			return "Title should not be empty!";
		} else if (activity.getTitle().trim().length() > 26) {
			return "Title length should be less then 26 character!";
		} else if (activity.getDescrizione() == null || activity.getDescrizione().trim().equals("")) {
			return "Description should not be empty!";
		} else if (activity.getActivityType() == ActivityType.VISITA_CULTURALE
				|| activity.getActivityType() == ActivityType.VOLONTARIATO) {

			if (activity.getStartDate().before(Date.from(Instant.now()))) {
				return "Start Date should not be before now!";
			} else if (activity.getStartDate().after(activity.getEndDate())) {
				return "Start date should not be after end date!";
			} else if (activity.getStartTime().after(activity.getEndTime())) {
				return "Start time should not be after end time!";
			}

		} else if (activity.getActivityType() == ActivityType.TERTULIA_A_TEMA) {

			if (activity.getStartDate().before(Date.from(Instant.now()))) {
				return "Start Date should not be before now!";
			} else if (activity.getStartTime().after(activity.getEndTime())) {
				return "Start time should not be after end time!";
			}

		}

		return null;
	}

	// Delete activity function
	public String deleteActivity(Long id) {

		if (id == null) {
			return "Activity id could not be null!";
		}

		Optional<Activity> activityOptional = activityRepository.findById(id);

		if (activityOptional.isPresent()) {

			activityRepository.deleteById(id);
			return null;

		} else {

			return "Activity does not exist!";

		}

	}

	// Update activity function
	@Transactional
	public String updateActivity(Long activityId, Date startDate, Date startTime, Date endTime) {

		if (activityId == null) {
			return "Activity id could not be null!";
		}
		
		Optional<Activity> activityOptional = activityRepository.findById(activityId);

		if (activityOptional.isEmpty()) {
			return "Activity not found!";
		} else {

			String err = activityValidator(activityOptional.get());

			if (err != null) {
				return err;
			} else {
				activityOptional.get().setStartDate(startDate);
				activityOptional.get().setStartTime(startTime);
				activityOptional.get().setEndTime(endTime);
			}
		}

		return null;

	}

	// Update activity state function
	@Transactional
	public String updateActivityState(Long id) {
		
		if (id == null) {
			return "Activity id could not be null!";
		}

		Optional<Activity> activityOptional = activityRepository.findById(id);

		if (activityOptional.isPresent()) {

			// Update state
			activityOptional.get().setState(true);

			// Send notification email to all
			for (AppUser user : userService.getUsers()) {

				emailSender.send(user.getEmail(), buildEmail(user.getName(), activityOptional.get()));

			}

			return null;

		} else {

			return "Activity not found!";
		}

	}

	// Return a list activities to be approved, without book
	public List<Activity> getActivitiesToApprove() {

		return activityRepository.findAllByStartDateGreaterThanAndStateAndActivityTypeNot(Date.from(Instant.now()),
				Boolean.FALSE, ActivityType.LIBRO);
	}

	// Return a list activities approved, without book
	public List<Activity> getActivitiesApproved() {

		return activityRepository.findAllByStartDateGreaterThanAndStateAndActivityTypeNot(Date.from(Instant.now()),
				Boolean.TRUE, ActivityType.LIBRO);
	}

	// Return a list of tertulie a tema to be approved
	public List<Activity> getAllTertulieToBeApproved() {

		return activityRepository.findAllByActivityTypeAndState(ActivityType.TERTULIA_A_TEMA, Boolean.FALSE);
	}

	// template for the notification email
	public String buildEmail(String name, Activity activity) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
				+ "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
				+ "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
				+ "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
				+ "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
				+ "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
				+ "                  \n" + "                    </td>\n"
				+ "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
				+ "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">New Activity!</span>\n"
				+ "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n"
				+ "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n"
				+ "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
				+ "      <td>\n" + "        \n"
				+ "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n"
				+ "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
				+ "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
				+ "  </tbody></table>\n" + "\n" + "\n" + "\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
				+ "        \n"
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name
				+ ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">A new activity has been added to the bulletin board!"
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Title:"
				+ activity.getTitle()
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Credits:"
				+ activity.getActivityCredits()
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Descrizione:"
				+ activity.getDescrizione() + "			 <p>See you soon</p>" + "        \n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
				+ "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
	}
}
