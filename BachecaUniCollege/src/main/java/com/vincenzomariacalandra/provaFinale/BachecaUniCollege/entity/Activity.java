package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

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
    
    @Column(length = 5000) 
    private String descrizione;
    
    private boolean state;
    
    private Integer maxNumberOfPartecipant;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date startTime;
    
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date endTime;
    
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

	public Activity(String title, String descrizione, boolean state, Date startDate, Date endDate, Date startTime, Date endTime,
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

	public Date getStartTime() {
		return startTime;
	}
	
	public String getStartTimeString() {
		return startTime.toString().substring(0, 5);
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	
	public String getEndTimeString() {
		return endTime.toString().substring(0, 5);
	}

	public void setEndTime(Date endTime) {
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
	
	public String getActivityTypeString() {
		return activityType.toString().replace("_", " ");
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public ActivityCredits getActivityCredits() {
		return activityCredits;
	}
	
	public double getActivityCreditsDouble() {
		return activityCredits.getVal();
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
	
    public Integer getMaxNumberOfPartecipant() {
		return maxNumberOfPartecipant;
	}

	public void setMaxNumberOfPartecipant(Integer maxNumberOfPartecipant) {
		this.maxNumberOfPartecipant = maxNumberOfPartecipant;
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
