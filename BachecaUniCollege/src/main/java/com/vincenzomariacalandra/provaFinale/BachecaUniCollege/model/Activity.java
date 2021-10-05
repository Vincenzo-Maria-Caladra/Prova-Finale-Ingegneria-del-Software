package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;


@Entity
@Table(name = "Activities")
public class Activity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
    private String title;
    
    private boolean state;
    
    private Date startDate;
    
    private Date endDate;
    
    private Time startTime;
    
    private Time endTime;
    
    @OneToMany(mappedBy = "activity")
    private List<UserActivity> userActivities;
    
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    
    private ActivityCredits activityCredits;

	protected Activity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Activity(String title, boolean state, Date startDate, Date endDate, Time startTime, Time endTime,
			ActivityType activityType, ActivityCredits activityCredits) {
		super();
		this.title = title;
		this.state = state;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityType = activityType;
		this.activityCredits = activityCredits;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public List<UserActivity> getUserActivities() {
		return userActivities;
	}

	public void setUserActivities(List<UserActivity> userActivities) {
		this.userActivities = userActivities;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public ActivityCredits getActivityCredits() {
		return activityCredits;
	}

	public void setActivityCredits(ActivityCredits activityCredits) {
		this.activityCredits = activityCredits;
	}
    
}
