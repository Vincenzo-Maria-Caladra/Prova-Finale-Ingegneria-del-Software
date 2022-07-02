package com.vincenzomariacalandra.provaFinale.BachecaUniCollege;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.pojo.RegistrationRequest;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.ConfirmationTokenService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.RegistrationService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service.UserService;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.EmailSender;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

/**
 * @author CalandraVM
 *
 */
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImpUnitTest {

	@Mock
	private UserService userService;

	@Mock
	private ConfirmationTokenService confirmationTokenService;

	@Mock
	private EmailSender emailSender;

	private RegistrationService service;

	@BeforeEach
	public void setUp() {

		service = new RegistrationService(userService, confirmationTokenService, emailSender);

	}

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(service);
	}

	@Test
	public void registerTest() {

		RegistrationRequest registrationRequest;
		registrationRequest = new RegistrationRequest();

		assertEquals("Registration request could not be null", service.register(null));

		registrationRequest.setEmail(null);
		assertEquals("Email not valid!", service.register(registrationRequest));

		registrationRequest.setEmail("example@mail.com.");
		assertEquals("Email not valid!", service.register(registrationRequest));

		registrationRequest.setEmail("example@mail.com");
		registrationRequest.setName(null);
		assertEquals("Name could not be empty!", service.register(registrationRequest));

		registrationRequest.setName("");
		assertEquals("Name could not be empty!", service.register(registrationRequest));

		registrationRequest.setName("Name0");
		assertEquals("Invalid characters in name!", service.register(registrationRequest));

		registrationRequest.setName("Name Secondname");
		registrationRequest.setSurname(null);
		assertEquals("Surname could not be empty!", service.register(registrationRequest));

		registrationRequest.setName("Name Jr'Second Name");
		registrationRequest.setSurname(null);
		assertEquals("Surname could not be empty!", service.register(registrationRequest));

		registrationRequest.setName("Name");
		registrationRequest.setSurname(null);
		assertEquals("Surname could not be empty!", service.register(registrationRequest));

		registrationRequest.setSurname("");
		assertEquals("Surname could not be empty!", service.register(registrationRequest));

		registrationRequest.setSurname("Surname9");
		assertEquals("Invalid characters in surname!", service.register(registrationRequest));

		registrationRequest.setSurname("Surname Second Sur'Name");
		registrationRequest.setPassword(null);
		assertEquals("Password could not be empty!", service.register(registrationRequest));

		registrationRequest.setSurname("Surname");
		registrationRequest.setPassword(null);
		assertEquals("Password could not be empty!", service.register(registrationRequest));

		registrationRequest.setPassword("");
		assertEquals("Password could not be empty!", service.register(registrationRequest));

		registrationRequest.setPassword("password1");
		registrationRequest.setConfirmPassword("password2");
		assertEquals("Passwords are not the same!", service.register(registrationRequest));

		registrationRequest.setPassword("password");
		registrationRequest.setConfirmPassword("password");
		assertEquals("Password must contains letters, numbers and special characters!",
				service.register(registrationRequest));

		registrationRequest.setPassword("P4ssw0rd!");
		registrationRequest.setConfirmPassword("P4ssw0rd!");
		assertEquals("Token generation Error", service.register(registrationRequest));
		reset(userService);

		lenient().when(userService.signUpUser(any())).thenReturn("123456789123456789123456789");
		assertNull(service.register(registrationRequest));
		verifySignUpUserIsCalledOnce();
	}

	@Test
	public void confirmationTokenTest() {
		String token = "123456789123456789123456789";
		ConfirmationToken confirmationToken = new ConfirmationToken();
		TemporalUnit tuH = ChronoUnit.HOURS;
		confirmationToken.setExpiredAt(LocalDateTime.now().minus(1, tuH));
		// confirmationToken.setConfirmedAt(LocalDateTime.now());

		assertEquals("Token could not be null or empty!", service.confirmToken(null));
		assertEquals("Token could not be null or empty!", service.confirmToken(""));

		lenient().when(confirmationTokenService.getToken(token)).thenReturn(Optional.ofNullable(null));
		assertEquals("Confirmation token not found!", service.confirmToken(token));

		lenient().when(confirmationTokenService.getToken(token)).thenReturn(Optional.of(confirmationToken));
		assertEquals("Token has expired", service.confirmToken(token));

		confirmationToken.setExpiredAt(LocalDateTime.now().plus(1, tuH));
		lenient().when(confirmationTokenService.getToken(token)).thenReturn(Optional.of(confirmationToken));
		assertEquals("Token email could not be null!", service.confirmToken(token));

		AppUser user = new AppUser("Name", "Surname", "example@email.com", "P4ssword!", UserType.STUDENTE);
		confirmationToken.setAppUser(user);

		lenient().when(confirmationTokenService.getToken(token)).thenReturn(Optional.of(confirmationToken));
		assertNull(service.confirmToken(token));

	}

	@Test
	private void buildEmailTest() {

		String name = "name";
		String link = "link";

		assertEquals(
				"<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
						"\n" +
						"<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
						"\n" +
						"  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
						+
						"    <tbody><tr>\n" +
						"      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
						"        \n" +
						"        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
						+
						"          <tbody><tr>\n" +
						"            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
						"                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
						+
						"                  <tbody><tr>\n" +
						"                    <td style=\"padding-left:10px\">\n" +
						"                  \n" +
						"                    </td>\n" +
						"                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
						+
						"                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
						+
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
						"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
						+
						"    <tbody><tr>\n" +
						"      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
						"      <td>\n" +
						"        \n" +
						"                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
						+
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
						"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
						+
						"    <tbody><tr>\n" +
						"      <td height=\"30\"><br></td>\n" +
						"    </tr>\n" +
						"    <tr>\n" +
						"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
						"      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
						+
						"        \n" +
						"            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
						+ name
						+ ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
						+ link
						+ "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
						+
						"        \n" +
						"      </td>\n" +
						"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
						"    </tr>\n" +
						"    <tr>\n" +
						"      <td height=\"30\"><br></td>\n" +
						"    </tr>\n" +
						"  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
						"\n" +
						"</div></div>",
				service.buildEmail(name, link));

	}

	@Test
	public void patternFindTest() {
		String pattern = "[^A-Za-z]";

		assertFalse(service.patternFind("GoodString", pattern));
		assertTrue(service.patternFind("B4dStr1ng", pattern));

	}

	@Test
	public void passwordValidationTest() {

		String goodPassword = "P4ssw0rd!";
		String badPassword = "password";

		assertFalse(service.passwordValidation(badPassword));
		assertTrue(service.passwordValidation(goodPassword));
	}

	private void verifySignUpUserIsCalledOnce() {
		verify(userService, VerificationModeFactory.only()).signUpUser(any());
		reset(userService);
	}

}
