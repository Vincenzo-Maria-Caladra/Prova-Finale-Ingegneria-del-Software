package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    
    private long tutorId;
    
    private boolean locked;
    
    private boolean enabled;
    
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    @OneToMany(mappedBy = "user")
    private List<UserActivity> usersActivities;

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
	
	@Override
	public String toString() {
		return "AppUser [id=" + id + ", name=" + name + ", surname=" + surname + ", username=" + username + ", email="
				+ email + ", password=" + password + ", tutorId=" + tutorId + ", locked=" + locked + ", enabled="
				+ enabled + ", userType=" + userType + ", usersActivities=" + usersActivities + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, enabled, id, locked, name, password, surname, tutorId, userType, username,
				usersActivities);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		return Objects.equals(email, other.email) && enabled == other.enabled && id == other.id
				&& locked == other.locked && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(surname, other.surname)
				&& tutorId == other.tutorId && userType == other.userType && Objects.equals(username, other.username)
				&& Objects.equals(usersActivities, other.usersActivities);
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

	public long getTutorId() {
		return tutorId;
	}

	public void setTutorId(long tutorId) {
		this.tutorId = tutorId;
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
	
}
