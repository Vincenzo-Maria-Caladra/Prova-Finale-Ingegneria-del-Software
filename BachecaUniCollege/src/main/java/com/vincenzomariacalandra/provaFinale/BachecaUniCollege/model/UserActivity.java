package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "Users_Activities")
public class UserActivity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="Users_id")
	private AppUser user;
	
	@ManyToOne
	@JoinColumn(name="Activities_id")
	private Activity activity;
	
	private boolean organizer;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public boolean isOrganizer() {
		return organizer;
	}

	public void setOrganizer(boolean organizer) {
		this.organizer = organizer;
	}

	@Override
	public String toString() {
		return "UserActivity [user=" + user + ", activity=" + activity + ", organizer=" + organizer + "]";
	}

	public UserActivity(AppUser user, Activity activity, boolean organizer) {
		super();
		this.user = user;
		this.activity = activity;
		this.organizer = organizer;
	}

	protected UserActivity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
