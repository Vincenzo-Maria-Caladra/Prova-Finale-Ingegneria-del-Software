<h1 style="text-align: center"> Documentation "Bacheca UniCollege" </h1>

<h3 style="text-align: center">Final Exam Of Software Engineering </h3>

<h4 style="text-align: center">Author: Calandra Vincenzo Maria </h4>



## Context

## Solution



## Database



### Authentication

When working with Spring Boot, the *spring-boot-starter-security* starter will automatically include all dependencies, such as *spring-security-core*, *spring-security-web*, and *spring-security-config* among others:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>2.3.3.RELEASE</version>
</dependency>
```

The Spring Security configuration class extends *WebSecurityConfigurerAdapter.*

By adding *@EnableWebSecurity*, we get Spring Security and MVC integration support:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public SecurityConfig(UserService userService, BCryptPasswordEncoder 							bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
```

**Starting with Spring 5, we also have to define a password encoder**. In our case, we'll use the *BCryptPasswordEncoder* defined in the *PasswordEncoder* class:

```java
@Configuration
public class PasswordEncoder {
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
```

The necessary configurations to Authorize Requests it allowing anonymous access on */login* so that users can authenticate. We'll restrict */admin* to *ADMIN* roles and securing everything else:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/v*/registration/**", "/login/**", "/error/**")
				.permitAll()
		.anyRequest()
			.authenticated().and()
			.formLogin()
			.loginPage("/login")
		    .failureUrl("/login-error")
		   .and()
		.logout();
	}
```

```java
@Override
protected void configure(final HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/admin/**").hasRole("ADMIN")
      .antMatchers("/anonymous*").anonymous()
      .antMatchers("/login*").permitAll()
      .anyRequest().authenticated()
      .and()
      // ...
}
```

Note that the order of the *antMatchers()* elements is significant; **the more specific rules need to come first, followed by the more general ones**.

Next we'll extend the above configuration for form login and logout:

```java
@Override
protected void configure(final HttpSecurity http) throws Exception {
    http
      // ...
      .and()
      .formLogin()
      .loginPage("/login.html")
      .loginProcessingUrl("/perform_login")
      .defaultSuccessUrl("/homepage.html", true)
      .failureUrl("/login.html?error=true")
      .failureHandler(authenticationFailureHandler())
      .and()
      .logout()
      .logoutUrl("/perform_logout")
      .deleteCookies("JSESSIONID")
      .logoutSuccessHandler(logoutSuccessHandler());
}
```

- *loginPage()* – the custom login page
- *loginProcessingUrl()* – the URL to submit the username and password to
- *defaultSuccessUrl()* – the landing page after a successful login
- *failureUrl()* – the landing page after an unsuccessful login
- *logoutUrl()* – the custom logout

The login form page is going to be registered with Spring MVC using the straightforward mechanism to [map views names to URLs](https://www.baeldung.com/spring-mvc-tutorial#configviews). Furthermore, there is no need for an explicit controller in between:

```html
<html>
<head></head>
<body>
   <h1>Login</h1>
   <form name='f' action="login" method='POST'>
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
</body>
</html>
```

The **Spring Login form** has the following relevant artifacts:

- *login* – the URL where the form is POSTed to trigger the authentication process
- *username* – the username
- *password* – the password

The default URL where the Spring Login will POST to trigger the authentication process is */login,* which used to be */j_spring_security_check* before [Spring Security 4](https://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-xml.html#m3to4-xmlnamespace-form-login). We can use the *loginProcessingUrl* method to override this URL:

```java
http.
  ...
  .formLogin()
  .loginProcessingUrl("/perform_login")
```

After successfully logging in, we will be redirected to a page that by default is the root of the web application.

We can override this via the *defaultSuccessUrl()* method:

```java
http.
  ...
  .formLogin()
  .defaultSuccessUrl("/homePage")
```

Similar to the Login Page, the Login Failure Page is autogenerated by Spring Security at */login?*error by default.

To override this, we can use the *failureUrl()* method:

```java
http.formLogin()
  .failureUrl("/login.html?error=true")
```

