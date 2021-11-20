package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.ActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserActivityRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityCredits;

/**
 * @author CalandraVM
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserActivityServiceImpUnitTest {
	

	@Mock
	private UserActivityRepository userActivityRepository;
	
	@Mock
	private ActivityRepository activityRepository;
	
	@Mock
	private UserRepository userRepository;
	
	private UserActivityService service;
	
	@BeforeEach
	private void setUp() {
		
		service = new UserActivityService(userActivityRepository, activityRepository, userRepository);
	
	}
	
	@Test
	public void insertNewUserActivityTest() {
		
		long idUser = 0; 
		long idActivity = 0; 
		boolean organizer = true;
		Activity activity = new Activity();
		AppUser appUser = new AppUser();
		UserActivity userActivity = new UserActivity();
		
		assertEquals("Invalid arguments for a new user activity", service.insertNewUserActivity(null, null, null));
		assertEquals("Invalid arguments for a new user activity", service.insertNewUserActivity(null, idActivity, organizer));
		assertEquals("Invalid arguments for a new user activity", service.insertNewUserActivity(idUser, null, organizer));
		assertEquals("Invalid arguments for a new user activity", service.insertNewUserActivity(idUser, idActivity, null));
		
		
		lenient().when(activityRepository.findById(idActivity)).thenReturn(Optional.ofNullable(null));
		assertEquals("Unknow activity with id:" + idActivity, service.insertNewUserActivity(idUser, idActivity, organizer));
	
		
		lenient().when(activityRepository.findById(idActivity)).thenReturn(Optional.of(activity));
		lenient().when(userRepository.findById(idUser)).thenReturn(Optional.ofNullable(null));
		assertEquals("Unknow user with id:" + idActivity, service.insertNewUserActivity(idUser, idActivity, organizer));
		
		lenient().when(activityRepository.findById(idActivity)).thenReturn(Optional.of(activity));
		lenient().when(userRepository.findById(idUser)).thenReturn(Optional.of(appUser));
		lenient().when(userActivityRepository.findByActivityAndOrganizer(activity, organizer)).thenReturn(Optional.of(userActivity));
		assertEquals("An Organizer is already set for actvity with id:" + idActivity, service.insertNewUserActivity(idUser, idActivity, organizer));
		
		lenient().when(activityRepository.findById(idActivity)).thenReturn(Optional.of(activity));
		lenient().when(userRepository.findById(idUser)).thenReturn(Optional.of(appUser));
		lenient().when(userActivityRepository.findByActivityAndOrganizer(activity, organizer)).thenReturn(Optional.ofNullable(null));
		assertNull(service.insertNewUserActivity(idUser, idActivity, organizer));
	
	}
	
	@Test
	public void deleteUserActivityByActivityIdTest () {
		
		Activity activity = new Activity();
		AppUser appUser = new AppUser();
		UserActivity userActivity = new UserActivity();
		
		assertEquals("User or activity are invalid or null object!", service.deleteUserActivityByActivityId(null, null));
		
		assertEquals("User activity not found!", service.deleteUserActivityByActivityId(appUser, activity));
		
		lenient().when(userActivityRepository.findByUserAndActivityAndOrganizer(appUser, activity, false)).thenReturn(Optional.of(userActivity));
		assertNull(service.deleteUserActivityByActivityId(appUser, activity));
		
	}
	
	@Test
	public void listAllUserActivitiesTest() {
		
		UserActivity userActivity1 = new UserActivity();
		UserActivity userActivity2 = new UserActivity();
		UserActivity userActivity3 = new UserActivity();
		List<UserActivity> list = List.of(userActivity1, userActivity2, userActivity3);
		
		lenient().when(userActivityRepository.findAll()).thenReturn(list);
		assertEquals(list, service.listAllUserActivities());
	}
	
	@Test
	public void listAllUserOfOneActivityTest() {
		
		UserActivity userActivity1 = new UserActivity();
		UserActivity userActivity2 = new UserActivity();
		UserActivity userActivity3 = new UserActivity();
		
		Activity activity = new Activity();
		
		List<UserActivity> list = List.of(userActivity1, userActivity2, userActivity3);
		
		lenient().when(userActivityRepository.findByActivity(activity)).thenReturn(list);
		assertEquals(list, service.listAllUserOfOneActivity(activity));
	}
	
	@Test
	public void listAllActivitiesOfUser() {
		
		UserActivity userActivity1 = new UserActivity();
		UserActivity userActivity2 = new UserActivity();
		UserActivity userActivity3 = new UserActivity();
		List<UserActivity> list = List.of(userActivity1, userActivity2, userActivity3);
		
		String user = "user@mail.com";
		AppUser appUser = new AppUser();
		
		lenient().when(userRepository.findByEmail(user)).thenReturn(Optional.of(appUser));
		lenient().when(userActivityRepository.findByUser(appUser)).thenReturn(list);
		assertEquals(list, service.listAllActivitiesOfUser(user));
	}
	
	@Test
	public void getUserActivityByUserAndActivityAndOrganizer () {
		
		Activity activity = new Activity();
		AppUser appUser = new AppUser();
		UserActivity userActivity = new UserActivity();
		
		String user = "user@mail.com";
		Long activityId = 0L;
		Boolean orgnizer = true;
		
		lenient().when(userRepository.findByEmail(user)).thenReturn(Optional.of(appUser));
		lenient().when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));
		lenient().when(userActivityRepository.findByUserAndActivityAndOrganizer(appUser, activity, orgnizer)).thenReturn(Optional.of(userActivity));
		assertEquals(Optional.of(userActivity), service.getUserActivityByUserAndActivityAndOrganizer(user, activityId, orgnizer));
		
	}
	
	@Test
	public void listAllUserActivitiesToApproveTest() {
		Long userId = 0L;
		
		UserActivity userActivity1 = new UserActivity();
		UserActivity userActivity2 = new UserActivity();
		UserActivity userActivity3 = new UserActivity();
		List<UserActivity> list = List.of(userActivity1, userActivity2, userActivity3);
		AppUser appUser = new AppUser();
		
		lenient().when(userRepository.findById(userId)).thenReturn(Optional.of(appUser));
		lenient().when(userActivityRepository.findByUserAndApproved(appUser, false)).thenReturn(list);
		assertEquals(list, service.listAllUserActivitiesToApprove(userId));
	}
	
	@Test
	public void updateUserActivityStateTest() {
		
		Long userActivityId = 0L;
		UserActivity userActivity = new UserActivity();
		lenient().when(userActivityRepository.findById(userActivityId)).thenReturn(Optional.of(userActivity));
		assertNull(service.updateUserActivityState(userActivityId));
		
		lenient().when(userActivityRepository.findById(userActivityId)).thenReturn(Optional.ofNullable(null));
		assertThrows( IllegalStateException.class ,() -> service.updateUserActivityState(userActivityId));
		
	}
	
	@Test
	public void getTotalApprovedCreditsTest() {
		
		Activity activity = new Activity();
		activity.setActivityCredits(ActivityCredits.TWO);
		
		UserActivity userActivity1 = new UserActivity();
		userActivity1.setOrganizer(true);
		userActivity1.setApproved(true);
		userActivity1.setActivity(activity);
		UserActivity userActivity2 = new UserActivity();
		userActivity2.setOrganizer(false);
		userActivity2.setApproved(true);
		userActivity2.setActivity(activity);
		UserActivity userActivity3 = new UserActivity();
		userActivity3.setOrganizer(false);
		userActivity3.setApproved(false);
		userActivity3.setActivity(activity);
		
		List<UserActivity> list = List.of(userActivity1, userActivity2, userActivity3);
		
		String user = "user@gmai.com";
		AppUser appUser = new AppUser();
		appUser.setEmail(user);
		
		lenient().when(userRepository.findByEmail(user)).thenReturn(Optional.of(appUser));
		lenient().when( userActivityRepository.findByUser(appUser)).thenReturn(list);
		assertEquals(8.89, service.getTotalApprovedCredits(appUser));
	}
	
	@Test
	public void getTotalToBeApprovedCreditsTest() {
		
		Activity activity = new Activity();
		activity.setActivityCredits(ActivityCredits.TWO);
		
		UserActivity userActivity1 = new UserActivity();
		userActivity1.setOrganizer(true);
		userActivity1.setApproved(true);
		userActivity1.setActivity(activity);
		UserActivity userActivity2 = new UserActivity();
		userActivity2.setOrganizer(false);
		userActivity2.setApproved(true);
		userActivity2.setActivity(activity);
		UserActivity userActivity3 = new UserActivity();
		userActivity3.setOrganizer(false);
		userActivity3.setApproved(false);
		userActivity3.setActivity(activity);
		
		List<UserActivity> list = List.of(userActivity1, userActivity2, userActivity3);
		
		String user = "user@gmai.com";
		AppUser appUser = new AppUser();
		appUser.setEmail(user);
		
		lenient().when(userRepository.findByEmail(user)).thenReturn(Optional.of(appUser));
		lenient().when( userActivityRepository.findByUser(appUser)).thenReturn(list);
		assertEquals(4.44, service.getTotalToBeApprovedCredits(appUser));
	}
	

}
