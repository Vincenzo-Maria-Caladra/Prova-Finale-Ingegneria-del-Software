package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author VectorCode
 *
 */
//Entity mapping User_Activities Table
@Entity
@Table(name= "Users_Activities")
public class UserActivity {
	
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
	
	private boolean approved;
	
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
	
	
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
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

	public UserActivity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
