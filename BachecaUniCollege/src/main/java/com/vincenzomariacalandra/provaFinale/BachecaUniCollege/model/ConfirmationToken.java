package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author VectorCode
 *
 */
//Entity mapping confirmation_token Table
@Entity
public class ConfirmationToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String token;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime expiredAt;
	
	private LocalDateTime confirmedAt;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private AppUser appUser;
	
	public ConfirmationToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, AppUser appUser) {
		super();
		
		this.token = token;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.appUser = appUser;
	}

	public long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}

	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}

	public void setConfirmedAt(LocalDateTime confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	@Override
	public String toString() {
		return "ConfirmationToken [id=" + id + ", token=" + token + ", createdAt=" + createdAt + ", expiredAt="
				+ expiredAt + ", confirmedAt=" + confirmedAt + ", appUser=" + appUser + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(appUser, confirmedAt, createdAt, expiredAt, id, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfirmationToken other = (ConfirmationToken) obj;
		return Objects.equals(appUser, other.appUser) && Objects.equals(confirmedAt, other.confirmedAt)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(expiredAt, other.expiredAt)
				&& id == other.id && Objects.equals(token, other.token);
	}
}
