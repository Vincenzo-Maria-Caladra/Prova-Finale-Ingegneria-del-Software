package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.EmailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author CalandraVM
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ActivityServiceImplUnitTest {
    
    @Mock
	private ActivityRepository activityRepository;

    @Mock
	private UserService userService;

    @Mock
	private EmailSender emailSender;

    private ActivityService service;
    
    @BeforeEach
    public void setUp() {
    	
    	service = new ActivityService(activityRepository, emailSender, userService);
    	
    }

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(service);
	}

	@Test
	public void findActivityByIdTest() throws Exception {

		Random rand = new Random();
		Long id = rand.nextLong();
		
		assertNull(service.findActivityById(null));
		
		
		reset(activityRepository);
		Activity activity = new Activity();
    	lenient().when(activityRepository.findById(id)).thenReturn(Optional.of(activity));
    	
		assertTrue(service.findActivityById(id).isPresent());
		verifyFindByIdIsCalledOnce(id);

	}

	// When it has been called, It should return null or different errors
	@Test
	public void addNewActivityTest() {

		Activity activity = null;
		assertEquals("Invalid activity!" , service.addNewActivity(activity));

		TemporalUnit day = ChronoUnit.DAYS;
		TemporalUnit hour = ChronoUnit.HOURS;

		activity = new Activity();
		activity.setTitle("Title");
		activity.setDescrizione("Descrizione");
		activity.setStartDate(Date.from(Instant.now().plus(1, day)));
		activity.setEndDate(Date.from(Instant.now().plus(1, day)));
		activity.setStartTime(Date.from(Instant.now().plus(1, hour)));
		activity.setStartTime(Date.from(Instant.now().plus(2, hour)));

		assertNull(service.addNewActivity(activity));
	}

	@Test
	public void activityValidatorTest() {

		Activity activity = null;
		assertEquals("Invalid activity!", service.addNewActivity(activity));

		activity = new Activity();
		activity.setTitle(null);
		assertEquals("Title should not be empty!", service.addNewActivity(activity));

		activity.setTitle("");
		assertEquals("Title should not be empty!", service.addNewActivity(activity));

		activity.setTitle("012345678901234567890123456");
		assertEquals( "Title length should be less then 26 character!", service.addNewActivity(activity));

		activity.setTitle("Title");
		activity.setDescrizione(null);
		assertEquals( "Description should not be empty!", service.addNewActivity(activity));

		activity.setDescrizione("");
		assertEquals( "Description should not be empty!", service.addNewActivity(activity));

		TemporalUnit tu = ChronoUnit.DAYS;
		activity.setDescrizione("Descrizione");

		activity.setActivityType(ActivityType.VISITA_CULTURALE);
		activity.setStartDate(Date.from(Instant.now().minus(1, tu)));
		assertEquals( "Start Date should not be before now!", service.addNewActivity(activity));

		activity.setActivityType(ActivityType.VOLONTARIATO);
		activity.setStartDate(Date.from(Instant.now().minus(1, tu)));
		assertEquals( "Start Date should not be before now!", service.addNewActivity(activity));

		activity.setStartDate(Date.from(Instant.now().plus(1, tu)));
		activity.setEndDate(Date.from(Instant.now()));
		assertEquals( "Start date should not be after end date!", service.addNewActivity(activity));

		activity.setStartDate(Date.from(Instant.now().plus(1, tu)));
		activity.setEndDate(Date.from(Instant.now().plus(1, tu)));
		tu = ChronoUnit.HOURS;

		activity.setStartTime(Date.from(Instant.now().plus(1, tu)));
		activity.setEndTime(Date.from(Instant.now()));
		assertEquals( "Start time should not be after end time!", service.addNewActivity(activity));

		tu = ChronoUnit.DAYS;
		activity.setActivityType(ActivityType.TERTULIA_A_TEMA);
		activity.setStartDate(Date.from(Instant.now().minus(1, tu)));
		assertEquals( "Start Date should not be before now!", service.addNewActivity(activity));

		activity.setActivityType(ActivityType.TERTULIA_A_TEMA);
		activity.setStartDate(Date.from(Instant.now().plus(1, tu)));
		tu = ChronoUnit.HOURS;

		activity.setStartTime(Date.from(Instant.now().plus(1, tu)));
		activity.setEndTime(Date.from(Instant.now()));
		assertEquals( "Start time should not be after end time!", service.addNewActivity(activity));

		tu = ChronoUnit.DAYS;
		activity.setStartDate(Date.from(Instant.now().plus(1, tu)));

		tu = ChronoUnit.HOURS;
		activity.setStartTime(Date.from(Instant.now()));
		activity.setEndTime(Date.from(Instant.now().plus(1, tu)));
		assertNull(service.addNewActivity(activity));

	}

	@Test
	public void deleteActivityTest() {
		
		Random rand = new Random();
		Long id = rand.nextLong();
		
	    Activity activity = new Activity();
	    Optional<Activity> optional = Optional.of(activity);
	    
	    reset(activityRepository);
	    
	    lenient().when(activityRepository.findById(id)).thenReturn(optional);
			    
		assertNull(service.deleteActivity(id));
		verifyFindByIdIsCalledOnce(id);
	    
		reset(activityRepository);
		lenient().when(activityRepository.findById(null)).thenReturn(optional);
		assertEquals("Activity id could not be null!", service.deleteActivity(null));
	}
	
	@Test
	public void updateActivityTest() {
		
		TemporalUnit tuD = ChronoUnit.DAYS;
		TemporalUnit tuH = ChronoUnit.HOURS;
		Date startDate = Date.from(Instant.now().plus(1, tuD));
		Date startTime = Date.from(Instant.now().plus(1, tuH));
		Date endTime = Date.from(Instant.now().plus(2, tuH));
		
		Random rand = new Random();
		Long id = rand.nextLong();
		
	    Activity activity = new Activity();
	    activity.setTitle("Title");
	    activity.setDescrizione("Description");
	    activity.setStartDate(Date.from(Instant.now().plus(1, tuD)));
	    activity.setEndDate(Date.from(Instant.now().plus(1, tuD)));
	    activity.setStartTime(Date.from(Instant.now().plus(1, tuH)));
	    activity.setEndTime(Date.from(Instant.now().plus(1, tuH)));
	    
	    reset(activityRepository);
	    lenient().when(activityRepository.findById(id)).thenReturn(Optional.ofNullable(null));
	    assertEquals("Activity not found!", service.updateActivity(id, startDate, startTime, endTime));
	    
	    reset(activityRepository);
	    lenient().when(activityRepository.findById(id)).thenReturn(Optional.of(activity));
	    assertNull(service.updateActivity(id, startDate, startTime, endTime));
	    verifyFindByIdIsCalledOnce(id);
	}
	
	@Test
	public void updateActivityStateTest() {
		
		Random rand = new Random();
		Long id = rand.nextLong();
		
	    Activity activity = new Activity();
	    
		assertEquals("Activity id could not be null!", service.updateActivityState(null));
	    
		reset(activityRepository);
		lenient().when(activityRepository.findById(id)).thenReturn(Optional.ofNullable(null));
		assertEquals("Activity not found!", service.updateActivityState(id));
		verifyFindByIdIsCalledOnce(id);
		
		reset(activityRepository);
		lenient().when(activityRepository.findById(id)).thenReturn(Optional.of(activity));
		assertNull(service.updateActivityState(id));
		verifyFindByIdIsCalledOnce(id);
	}
	
	@Test
	public void getActivitiesToApproveTest() {
		Date date = Date.from(Instant.now());
		List<Activity> list = List.of(new Activity(), new Activity(), new Activity());
		
		reset(activityRepository);
		lenient().when(activityRepository.findAllByStartDateGreaterThanAndStateAndActivityTypeNot(date,
				false, ActivityType.LIBRO)).thenReturn(list);
		assertEquals(list, service.getActivitiesToApprove());
		verifyFindAllByStartDateGreaterThanAndStateAndActivityTypeNotIsCalledOnce(date, false, ActivityType.LIBRO);
	}
	
//	@Test
//	public void getActivitiesApprovedTest() {
//		Date date = Date.from(Instant.now());
//		List<Activity> list = List.of(new Activity(), new Activity(), new Activity());
//		
//		reset(activityRepository);
//		lenient().when(activityRepository.findAllByStartDateGreaterThanAndStateAndActivityTypeNot(date,
//				true, ActivityType.LIBRO)).thenReturn(list);
//		assertEquals(list, service.getActivitiesToApprove());
//		verifyFindAllByStartDateGreaterThanAndStateAndActivityTypeNotIsCalledOnce(date, true, ActivityType.LIBRO);
//	}
	
	@Test
	public void getAllTertulieToBeApprovedTest() {
		
		ActivityType aT = ActivityType.TERTULIA_A_TEMA;
		Boolean state = false;
		List<Activity> list1 = List.of(new Activity(), new Activity(), new Activity());
		
		reset(activityRepository);
		lenient().when(activityRepository.findAllByActivityTypeAndState(aT, state)).thenReturn(list1);
		assertEquals(list1, service.getAllTertulieToBeApproved());
		verifyFindAllByActivityTypeAndStateIsCalledOnce(aT, state);
	}
	
	@Test
	public void buildEmailTest () {
		
		String name = "name";
		Activity activity = new Activity();
		activity.setTitle("Title");
		activity.setDescrizione("Descrizione");
		activity.setActivityType(ActivityType.VISITA_CULTURALE);
		
		String email = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
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
	
		assertEquals(email, service.buildEmail(name, activity));
	}
	
    private void verifyFindByIdIsCalledOnce(Long id) {
        verify(activityRepository, VerificationModeFactory.times(1)).findById(id);
        reset(activityRepository);
    }
    
    private void verifyFindAllByStartDateGreaterThanAndStateAndActivityTypeNotIsCalledOnce(Date date, boolean state, ActivityType activityType) {
        verify(activityRepository, VerificationModeFactory.times(1)).findAllByStartDateGreaterThanAndStateAndActivityTypeNot(date,
        		state, activityType);
        reset(activityRepository);
    }
    
    private void verifyFindAllByActivityTypeAndStateIsCalledOnce(ActivityType aT, Boolean state) {
    	verify(activityRepository, VerificationModeFactory.times(1)).findAllByActivityTypeAndState(aT, state);
    }

}
