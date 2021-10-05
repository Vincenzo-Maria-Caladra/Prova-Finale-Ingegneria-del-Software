package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Entity
@Table(name = "Users")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
    private String name;
    
    private String surname;
    
    private String email;
    
    private String password;
    
    private long tutorId;
    
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    @OneToMany(mappedBy = "user")
    private List<UserActivity> usersActivities;

	protected User() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getPassword() {
		return password;
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

	public User(String name, String surname, String email, String password, UserType userType,
			List<UserActivity> usersActivities, long tutorId) {
		super();

		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.usersActivities = usersActivities;
		this.tutorId = tutorId;
	}
	
	
}
