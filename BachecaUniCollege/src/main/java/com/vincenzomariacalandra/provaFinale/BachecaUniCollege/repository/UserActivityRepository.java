package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
	
	//Return an userActivity, if exists, by userActivity's activity and organizer value
	Optional<UserActivity> findByActivityAndOrganizer(Activity activity, boolean organizer);
	
	//Return a list of userActivities, if they exist, by userActivity's activity
	List<UserActivity> findByActivity(Activity activity);
	
	//Return a list of userActivities, if they exist, by userActivity's user
	List<UserActivity> findByUser(AppUser appUser);
	
	//Return an userActivity, if exists, by userActivity's user, actvity and organizer
	Optional<UserActivity> findByUserAndActivityAndOrganizer(AppUser user, Activity activity, boolean organizer);

	//Return an userActivities, if exists, by userActivity's user and activity
	Optional<UserActivity> findByUserAndActivity(AppUser user, Activity activity);
	
	//Return a list of userActivities, if they exist, by userActivity's user and approved properties
	List<UserActivity> findByUserAndApproved(AppUser user, boolean approved);
	
	
}
