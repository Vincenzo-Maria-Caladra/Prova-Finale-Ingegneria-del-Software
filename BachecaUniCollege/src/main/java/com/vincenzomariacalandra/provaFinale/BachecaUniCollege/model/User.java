package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	public User(long user_id, String user_name, String user_surname, String user_email, String user_password) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_surname = user_surname;
		this.user_email = user_email;
		this.user_password = user_password;
	}

	protected User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(user_email, user_id, user_name, user_password, user_surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(user_email, other.user_email) && user_id == other.user_id
				&& Objects.equals(user_name, other.user_name) && Objects.equals(user_password, other.user_password)
				&& Objects.equals(user_surname, other.user_surname);
	}
	
}
