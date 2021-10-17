package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
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
    
    private String descrizione;
    
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

	public Activity() {
		super();
	}

	public Activity(String title, String descrizione, boolean state, Date startDate, Date endDate, Time startTime, Time endTime,
			ActivityType activityType, ActivityCredits activityCredits) {
		super();
		this.title = title;
		this.descrizione = descrizione;
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

	public void setStartTime(String startTime) {
		System.out.println("SOno Qui");
		this.startTime = Time.valueOf(LocalTime.parse(startTime));
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = Time.valueOf(LocalTime.parse(endTime));
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", title=" + title + ", descrizione=" + descrizione + ", state=" + state
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", endTime="
				+ endTime + ", userActivities=" + userActivities + ", activityType=" + activityType
				+ ", activityCredits=" + activityCredits + "]";
	}
	
	
	
    
}
