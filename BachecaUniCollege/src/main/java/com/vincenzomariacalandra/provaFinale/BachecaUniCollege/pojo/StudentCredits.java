package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.pojo;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;

/**
 * @author VectorCode
 *
 */
//Pojo for Activities
public class StudentCredits {
	
	public AppUser user;
	
	public double approvedCredits;
	
	public double notApprovedCredits;

	public AppUser getUser() {
		return user;
	}
	
	public void setUser(AppUser user) {
		this.user = user;
	}
	
	public double getApprovedCredits() {
		return approvedCredits;
	}

	public void setApprovedCredits(double approvedCredits) {
		this.approvedCredits = approvedCredits;
	}

	public double getNotApprovedCredits() {
		return notApprovedCredits;
	}

	public void setNotApprovedCredits(double notApprovedCredits) {
		this.notApprovedCredits = notApprovedCredits;
	}

	public StudentCredits() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
