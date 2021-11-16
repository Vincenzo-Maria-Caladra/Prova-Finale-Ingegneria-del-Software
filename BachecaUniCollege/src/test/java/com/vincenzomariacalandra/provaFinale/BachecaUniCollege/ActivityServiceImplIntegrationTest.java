package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
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

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ActivityServiceImplIntegrationTest {
    
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
		
		Date startDate;
		Date startTime; 
		Date endTime;
		
		Random rand = new Random();
		Long id = rand.nextLong();
		
	    Activity activity = new Activity();
	    Optional<Activity> optional = Optional.of(activity);
	    
	    reset(activityRepository);
	    lenient().when(activityRepository.findById(id)).thenReturn(Optional.ofNullable(null));
	    assertEquals("Activity not found!", service.updateActivity(id, startDate, startTime, endTime));
	}
	
	
    private void verifyFindByIdIsCalledOnce(Long id) {
        verify(activityRepository, VerificationModeFactory.times(1)).findById(id);
        reset(activityRepository);
    }

}
