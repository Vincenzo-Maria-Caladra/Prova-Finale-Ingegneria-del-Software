package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Entity
@Table(name = "Users")
public class AppUser implements UserDetails{
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
    private String name;
    
    private String surname;
    
    private String username;
    
    private String email;
    
    private String password;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser tutor;
    
    private boolean locked;
    
    private boolean enabled;
    
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    @OneToMany(mappedBy = "user", cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserActivity> usersActivities;
    
    @OneToMany(mappedBy = "appUser", cascade= {CascadeType.PERSIST, CascadeType.REMOVE} )
    private List<ConfirmationToken> confirmationTokens;

	protected AppUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AppUser(String name, String surname, String email, String password, UserType userType) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.enabled = false;
		this.locked = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<UserActivity> getUsersActivities() {
		return usersActivities;
	}

	public void setUsersActivities(List<UserActivity> usersActivities) {
		this.usersActivities = usersActivities;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userType.name());
		
		return Collections.singletonList(simpleGrantedAuthority);
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isDirettore() {
		
		if (userType.name().equals("DIRETTORE")) {
			return true;
		}
		
		return false;
	}
	
	public boolean isTutor() {
		
		if (userType.name().equals("TUTOR")) {
			return true;
		}
		
		return false;
	}
	
	public boolean isSegreteria() {
		
		if (userType.name().equals("SEGRETERIA")) {
			return true;
		}
		
		return false;
	}
	
	public boolean isStudente() {
		
		if (userType.name().equals("STUDENTE")) {
			return true;
		}
		
		return false;
	}

	public AppUser getTutor() {
		return tutor;
	}

	public void setTutor(AppUser tutor) {
		this.tutor = tutor;
	}
	
	
}
