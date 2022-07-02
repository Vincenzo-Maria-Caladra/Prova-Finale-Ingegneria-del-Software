package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.UserActivity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.pojo.StudentCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository.UserRepository;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ConfirmationTokenService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserActivityService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author CalandraVM
 *
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImpUnitTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserActivityService userActivityService;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private ConfirmationTokenService confirmationTokenService;

	private UserService service;

	@BeforeEach
	public void setUp() {

		service = new UserService(userRepository, bCryptPasswordEncoder, confirmationTokenService, userActivityService);

	}

	@Test
	public void getUsersTest() {

		AppUser user1 = new AppUser();
		AppUser user2 = new AppUser();
		AppUser user3 = new AppUser();
		List<AppUser> list = List.of(user1, user2, user3);

		lenient().when(userRepository.findAll()).thenReturn(list);
		assertEquals(list, service.getUsers());
		verify(userRepository, atLeastOnce()).findAll();
		reset(userRepository);
	}

	@Test
	public void removeUserTest() {

		Long userId = 0L;
		AppUser user = new AppUser();

		assertEquals("User id must not be null!", service.removeUser(null));

		lenient().when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null));
		assertEquals("User with Id: " + userId + " does not exist!", service.removeUser(userId));
		verify(userRepository, atLeastOnce()).findById(userId);
		reset(userRepository);

		lenient().when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		assertNull(service.removeUser(userId));
		verify(userRepository, atLeastOnce()).findById(userId);
		reset(userRepository);
	}

	@Test
	public void loadUserByUsernameTest() {

		String email = "user@mail.it";
		AppUser user = new AppUser();

		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(null));
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(""));
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("  "));

		lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		assertEquals(user, service.loadUserByUsername(email));
		verify(userRepository, atLeastOnce()).findByEmail(email);
		reset(userRepository);

		lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(null));
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(email));
		verify(userRepository, atLeastOnce()).findByEmail(email);
		reset(userRepository);
	}

	@Test
	public void signUpUserTest() {

		String email = "user@mail.it";
		AppUser user = new AppUser();
		user.setEnabled(true);
		user.setEmail(email);

		lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(null));
		assertNotNull(service.signUpUser(user));
		verify(userRepository).findByEmail(email);
		reset(userRepository);

		lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		assertThrows(IllegalStateException.class, () -> service.signUpUser(user));
		verify(userRepository).findByEmail(email);
		reset(userRepository);
	}

	@Test
	public void enableAppUserTest() {

		String email = "user@mail.it";
		AppUser user = new AppUser();
		user.setEnabled(true);
		user.setEmail(email);

		lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(null));
		assertEquals("Unable to enable the app user account! It doesnt exists!", service.enableAppUser(email));
		verify(userRepository).findByEmail(email);
		reset(userRepository);

		lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		assertNull(service.enableAppUser(email));
		verify(userRepository).findByEmail(email);
		reset(userRepository);

	}

	@Test
	public void getAllAppUserStudentiTest() {

		AppUser user1 = new AppUser();
		AppUser user2 = new AppUser();
		AppUser user3 = new AppUser();
		List<AppUser> list = List.of(user1, user2, user3);

		lenient().when(userRepository.findAllByUserType(UserType.STUDENTE)).thenReturn(list);
		assertEquals(list, service.getAllAppUserStudenti());
		verify(userRepository, atLeastOnce()).findAllByUserType(UserType.STUDENTE);
		reset(userRepository);
	}

	@Test
	public void getAllAppUserTutorTest() {

		AppUser user1 = new AppUser();
		AppUser user2 = new AppUser();
		AppUser user3 = new AppUser();
		List<AppUser> list = List.of(user1, user2, user3);

		lenient().when(userRepository.findAllByUserType(UserType.TUTOR)).thenReturn(list);
		assertEquals(list, service.getAllAppUserTutor());
		verify(userRepository, atLeastOnce()).findAllByUserType(UserType.TUTOR);
		reset(userRepository);
	}

	@Test
	public void getAllUsersCredits() {

		String email = "user@email.com";

		AppUser user1 = new AppUser();
		user1.setEmail(email);
		AppUser user2 = new AppUser();
		user2.setEmail(email);
		AppUser user3 = new AppUser();
		user3.setEmail(email);

		List<AppUser> list = List.of(user1, user2, user3);

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

		List<UserActivity> listOfUserActivity = List.of(userActivity1, userActivity2, userActivity3);

		StudentCredits userCredits1 = new StudentCredits();
		userCredits1.setUser(user1);
		userCredits1.setApprovedCredits(8.89);
		userCredits1.setNotApprovedCredits(4.44);

		StudentCredits userCredits2 = new StudentCredits();
		userCredits2.setUser(user1);
		userCredits2.setApprovedCredits(8.89);
		userCredits2.setNotApprovedCredits(4.44);

		StudentCredits userCredits3 = new StudentCredits();
		userCredits3.setUser(user1);
		userCredits3.setApprovedCredits(8.89);
		userCredits3.setNotApprovedCredits(4.44);

		List<StudentCredits> creditsList = List.of(userCredits1, userCredits2, userCredits3);

		lenient().when(userRepository.findAllByUserType(UserType.STUDENTE)).thenReturn(list);
		lenient().when(userActivityService.listAllActivitiesOfUser(email)).thenReturn(listOfUserActivity);
		assertEquals(creditsList.get(0).getApprovedCredits(), service.getAllUsersCredits().get(0).getApprovedCredits());
		assertEquals(creditsList.get(0).getNotApprovedCredits(),
				service.getAllUsersCredits().get(0).getNotApprovedCredits());
		assertEquals(creditsList.get(1).getApprovedCredits(), service.getAllUsersCredits().get(1).getApprovedCredits());
		assertEquals(creditsList.get(1).getNotApprovedCredits(),
				service.getAllUsersCredits().get(1).getNotApprovedCredits());
		assertEquals(creditsList.get(2).getApprovedCredits(), service.getAllUsersCredits().get(2).getApprovedCredits());
		assertEquals(creditsList.get(2).getNotApprovedCredits(),
				service.getAllUsersCredits().get(2).getNotApprovedCredits());

		verify(userRepository, atLeastOnce()).findAllByUserType(UserType.STUDENTE);
		reset(userRepository);
	}

	@Test
	public void getAllMenteeByTutorTest() {

		AppUser user1 = new AppUser();
		AppUser user2 = new AppUser();
		AppUser user3 = new AppUser();
		List<AppUser> list = List.of(user1, user2, user3);

		AppUser tutor = new AppUser();

		lenient().when(userRepository.findAllByTutor(tutor)).thenReturn(list);
		assertEquals(list, service.getAllMenteeByTutor(tutor));
		verify(userRepository, atLeastOnce()).findAllByTutor(tutor);
		reset(userRepository);

	}

	@Test
	public void getAllMenteeCreditsByTutorTest() {

		String email = "user@email.com";

		AppUser user1 = new AppUser();
		user1.setEmail(email);
		AppUser user2 = new AppUser();
		user2.setEmail(email);
		AppUser user3 = new AppUser();
		user3.setEmail(email);

		List<AppUser> list = List.of(user1, user2, user3);

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

		List<UserActivity> listOfUserActivity = List.of(userActivity1, userActivity2, userActivity3);

		StudentCredits userCredits1 = new StudentCredits();
		userCredits1.setUser(user1);
		userCredits1.setApprovedCredits(8.89);
		userCredits1.setNotApprovedCredits(4.44);

		StudentCredits userCredits2 = new StudentCredits();
		userCredits2.setUser(user1);
		userCredits2.setApprovedCredits(8.89);
		userCredits2.setNotApprovedCredits(4.44);

		StudentCredits userCredits3 = new StudentCredits();
		userCredits3.setUser(user1);
		userCredits3.setApprovedCredits(8.89);
		userCredits3.setNotApprovedCredits(4.44);

		List<StudentCredits> creditsList = List.of(userCredits1, userCredits2, userCredits3);

		AppUser tutor = new AppUser();

		lenient().when(userRepository.findAllByTutor(tutor)).thenReturn(list);
		lenient().when(userActivityService.listAllActivitiesOfUser(email)).thenReturn(listOfUserActivity);
		assertEquals(creditsList.get(0).getApprovedCredits(),
				service.getAllMenteeCreditsByTutor(tutor).get(0).getApprovedCredits());
		assertEquals(creditsList.get(0).getNotApprovedCredits(),
				service.getAllMenteeCreditsByTutor(tutor).get(0).getNotApprovedCredits());
		assertEquals(creditsList.get(1).getApprovedCredits(),
				service.getAllMenteeCreditsByTutor(tutor).get(1).getApprovedCredits());
		assertEquals(creditsList.get(1).getNotApprovedCredits(),
				service.getAllMenteeCreditsByTutor(tutor).get(1).getNotApprovedCredits());
		assertEquals(creditsList.get(2).getApprovedCredits(),
				service.getAllMenteeCreditsByTutor(tutor).get(2).getApprovedCredits());
		assertEquals(creditsList.get(2).getNotApprovedCredits(),
				service.getAllMenteeCreditsByTutor(tutor).get(2).getNotApprovedCredits());

		verify(userRepository, atLeastOnce()).findAllByTutor(tutor);
		reset(userRepository);
	}

	@Test
	public void updateUserTutorTest() {

		String tutorId = "0";
		Long menteeId = 0L;

		AppUser user1 = new AppUser();
		AppUser user2 = new AppUser();

		lenient().when(userRepository.findById(Long.valueOf(tutorId))).thenReturn(Optional.of(user1));
		lenient().when(userRepository.findById(menteeId)).thenReturn(Optional.of(user2));
		assertNull(service.updateUserTutor(menteeId, tutorId));
		verify(userRepository, atMost(2)).findById(anyLong());
		reset(userRepository);

		lenient().when(userRepository.findById(Long.valueOf(tutorId))).thenReturn(Optional.ofNullable(null));
		lenient().when(userRepository.findById(menteeId)).thenReturn(Optional.ofNullable(null));
		assertEquals("Error! User or Tutor doesnt exists!", service.updateUserTutor(menteeId, tutorId));
		verify(userRepository, atMost(2)).findById(anyLong());
		reset(userRepository);
	}

	@Test
	public void updateUserTypeTest() {

		String type = "TUTOR";
		Long userId = 0L;

		AppUser user1 = new AppUser();

		lenient().when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
		assertNull(service.updateUserType(userId, type));
		verify(userRepository, atLeastOnce()).findById(anyLong());
		reset(userRepository);

		lenient().when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null));
		assertEquals("Error! User doesnt exists!", service.updateUserType(userId, type));
		verify(userRepository, atLeastOnce()).findById(anyLong());
		reset(userRepository);
	}

}
