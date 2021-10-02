package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;
    
    private String user_name;
    
    private String user_surname;
    
    private String user_email;
    
    private String user_password;
    
    @OneToMany(mappedBy = "user")
    private List<UserActivity> usersActivities; 
    

	public List<UserActivity> getUsersActivities() {
		return usersActivities;
	}

	public void setUsersActivities(List<UserActivity> usersActivities) {
		this.usersActivities = usersActivities;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_surname() {
		return user_surname;
	}

	public void setUser_surname(String user_surname) {
		this.user_surname = user_surname;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	
	protected User(long user_id, String user_name, String user_surname, String user_email, String user_password,
			List<UserActivity> usersActivities) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_surname = user_surname;
		this.user_email = user_email;
		this.user_password = user_password;
		this.usersActivities = usersActivities;
	}

	protected User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_surname=" + user_surname
				+ ", user_email=" + user_email + ", user_password=" + user_password + ", usersActivities="
				+ usersActivities + "]";
	}
	
}
