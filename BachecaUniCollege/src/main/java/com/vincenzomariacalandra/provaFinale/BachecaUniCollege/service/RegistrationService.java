package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.ConfirmationToken;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.pojo.RegistrationRequest;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.EmailSender;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

/**
 * @author VectorCode
 *
 */
@Service
public class RegistrationService {
	
	private final UserService userService;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSender emailSender;
	
	@Autowired
	public RegistrationService(UserService userService, 
			ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
		super();
		this.userService = userService;
		this.confirmationTokenService = confirmationTokenService;
		this.emailSender = emailSender;
	}

	// handle registration
	public String register(RegistrationRequest registrationRequest) {
		
		
		//Check if the email is Valid
		//EmailValidator class validate email which uses RFC 822 standards
		if (!EmailValidator.getInstance().isValid(registrationRequest.getEmail())) {
			return "Email not valid!";
		}
		
		//Check name field
		if (registrationRequest.getName().trim().isBlank() || registrationRequest.getName().trim().isBlank()) {
			return "Name could not be empty!";
		} else if ( patternFind(registrationRequest.getName().trim(), "[^A-Za-z0-9]")) {
			return "Invalid characters in name!";
		}
		
		//Check surname field
		if (registrationRequest.getSurname().trim().isBlank() || registrationRequest.getSurname().trim().isBlank()) {
			return "Surname could not be empty!";
		} else if ( patternFind(registrationRequest.getSurname().trim(), "[^A-Za-z0-9]")) {
			return "Invalid characters in surname!";
		}
		
		//Check password field
		if (registrationRequest.getPassword().isBlank() || registrationRequest.getPassword().isBlank()) {
			return "Password could not be empty!";
		} else if (passwordValidation(registrationRequest.getPassword())) {
			return "Password must contains letters, numbers and special characters!";
		}
		
		//Register a new user
		String token = userService.signUpUser(
				new AppUser(
						registrationRequest.getName().trim(),
						registrationRequest.getSurname().trim(), 
						registrationRequest.getEmail().trim(),
						registrationRequest.getPassword(), 
						UserType.STUDENTE));
		
		//Generate user token link where to validate the registration
		String link = "http://localhost:8080/registration/confirm?token="+token;
		
		//Sending the confirmation email
		emailSender.send(registrationRequest.getEmail(), buildEmail(registrationRequest.getName(), link));
		
		return null;
	}
	
	//handle signUp confirmation
    @Transactional
    public String confirmToken(String token) {
    	
    	//Check if the token exist and if it has been exipered
        Optional<ConfirmationToken> confirmationToken = confirmationTokenService.getToken(token);
        
        if (confirmationToken.isEmpty()) {
            return "Confirmation token not found!";
        } else if (confirmationToken.get().getExpiredAt().isBefore(LocalDateTime.now())) {
        	return "Token has expired";
        }

        //Set confirmation localDateTime
        confirmationTokenService.setConfirmedAt(token);
        
        //Enable the user account
        userService.enableAppUser( confirmationToken.get().getAppUser().getEmail());
        
        return null;
    }
    
    // template for the confirmation email
    private String buildEmail(String name, String link) {
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
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
    
    //Utility method to check if a string contains a gived list of character
    public static boolean patternFind(String stringToTest, String listOfCharacter) {
        return Pattern.compile(listOfCharacter)
          .matcher(stringToTest)
          .find();
    }
    
    //Utility method to check password if the password contains
    //LETTERS, SPECIAL CHARACTERS and NUMBERS with at least 8 digit
    public static boolean passwordValidation(String password) 
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");


               Matcher hasLetter = letter.matcher(password);
               Matcher hasDigit = digit.matcher(password);
               Matcher hasSpecial = special.matcher(password);

               return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
    
}
