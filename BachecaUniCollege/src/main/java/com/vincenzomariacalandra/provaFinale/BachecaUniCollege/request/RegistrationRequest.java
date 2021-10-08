package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.request;

import java.util.Objects;


public class RegistrationRequest {
	
	private final String name;
	private final String surname;
	private final String email;
	private final String password;
	
	
	public RegistrationRequest(String name, String surname, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
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
