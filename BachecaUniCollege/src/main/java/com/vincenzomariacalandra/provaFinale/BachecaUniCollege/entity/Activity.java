package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityCredits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;


/**
 * @author VectorCode
 *
 */
//Entity mapping Activities Table
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
    
    @Column(nullable = true, length = 64)
    private String photo;
    
    @OneToMany(mappedBy = "activity", cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserActivity> userActivities;
    
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    
    private ActivityCredits activityCredits;

	public Activity() {
		super();
	}

	public Activity(String title, String descrizione, boolean state, Date startDate, Date endDate, Time startTime, Time endTime,
			ActivityType activityType, ActivityCredits activityCredits, String photo) {
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
		this.photo = photo;
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
    @Transient
    public String getPhotosImagePath() {
        if (photo == null) {
        	return null;
        }
        return "/activity-photos/" + id + "/" + photo;
    }

	@Override
	public String toString() {
		return "Activity [id=" + id + ", title=" + title + ", descrizione=" + descrizione + ", state=" + state
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", endTime="
				+ endTime + ", userActivities=" + userActivities + ", activityType=" + activityType
				+ ", activityCredits=" + activityCredits + "]";
	}
	
	
	
    
}