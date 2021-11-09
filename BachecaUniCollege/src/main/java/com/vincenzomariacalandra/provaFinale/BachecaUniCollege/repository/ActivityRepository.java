package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.ActivityType;


/**
 * @author VectorCode
 *
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	//Return an activity, if exists, by the activity's id
	Optional<Activity> findById(long id);
	
	//Return an activity, if exists, by the activity's title
	List<Activity> findByTitle(String title);
	
	//Return a list of activities, if they exist, by not the activity's title specified and the state
	List<Activity> findAllByStartDateGreaterThanAndStateAndActivityTypeNot(Date startDate, boolean state, ActivityType activityType);

	//Return a list of activities, if they exist, by the activity's type and state
	List<Activity> findAllByActivityTypeAndState(ActivityType tertuliaATema, Boolean state);
}