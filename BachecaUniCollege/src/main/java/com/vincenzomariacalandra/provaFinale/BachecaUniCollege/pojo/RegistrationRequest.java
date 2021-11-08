package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.pojo;

import java.util.Objects;

/**
 * @author VectorCode
 *
 */
//Pojo for Registration request
public class RegistrationRequest {
	
	private String name;
	private String surname;
	private String email;
	private String password;
	
	
	public RegistrationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, name, password, surname);
	}

	@Override
	public String toString() {
		return "RegistrationService [name=" + name + ", surname=" + surname + ", email="
				+ email + ", password=" + password + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationRequest other = (RegistrationRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(surname, other.surname);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}

}
