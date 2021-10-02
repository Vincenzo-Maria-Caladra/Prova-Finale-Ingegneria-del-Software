package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.Activity_credits;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.Activity_type;

@Entity
@Table(name = "Activities")
public class Activity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long activity_id;
    
    private String activity_title;
    
    private boolean activity_state;
    
    private Date activity_start_date;
    
    private Date activity_end_date;
    
    private Time activity_start_time;
    
    private Time activity_end_time;
    
    @Enumerated(EnumType.STRING)
    private Activity_type activity_type;
    
    private Activity_credits activity_credits;

	public long getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}

	public String getActivity_title() {
		return activity_title;
	}

	public void setActivity_title(String activity_title) {
		this.activity_title = activity_title;
	}

	public boolean isActivity_state() {
		return activity_state;
	}

	public void setActivity_state(boolean activity_state) {
		this.activity_state = activity_state;
	}

	public Date getActivity_start_date() {
		return activity_start_date;
	}

	public void setActivity_start_date(Date activity_start_date) {
		this.activity_start_date = activity_start_date;
	}

	public Date getActivity_end_date() {
		return activity_end_date;
	}

	public void setActivity_end_date(Date activity_end_date) {
		this.activity_end_date = activity_end_date;
	}

	public Time getActivity_start_time() {
		return activity_start_time;
	}

	public void setActivity_start_time(Time activity_start_time) {
		this.activity_start_time = activity_start_time;
	}

	public Time getActivity_end_time() {
		return activity_end_time;
	}

	public void setActivity_end_time(Time activity_end_time) {
		this.activity_end_time = activity_end_time;
	}

	public Activity_type getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(Activity_type activity_type) {
		this.activity_type = activity_type;
	}

	public Activity_credits getActivity_credits() {
		return activity_credits;
	}

	public void setActivity_credits(Activity_credits activity_credits) {
		this.activity_credits = activity_credits;
	}

	public Activity(long activity_id, String activity_title, boolean activity_state, Date activity_start_date,
			Date activity_end_date, Time activity_start_time, Time activity_end_time, Activity_type activity_type,
			Activity_credits activity_credits) {
		super();
		this.activity_id = activity_id;
		this.activity_title = activity_title;
		this.activity_state = activity_state;
		this.activity_start_date = activity_start_date;
		this.activity_end_date = activity_end_date;
		this.activity_start_time = activity_start_time;
		this.activity_end_time = activity_end_time;
		this.activity_type = activity_type;
		this.activity_credits = activity_credits;
	}

	protected Activity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Activity [activity_id=" + activity_id + ", activity_title=" + activity_title + ", activity_state="
				+ activity_state + ", activity_start_date=" + activity_start_date + ", activity_end_date="
				+ activity_end_date + ", activity_start_time=" + activity_start_time + ", activity_end_time="
				+ activity_end_time + ", activity_type=" + activity_type + ", activity_credits=" + activity_credits
				+ "]";
	}
    
    
    
}
