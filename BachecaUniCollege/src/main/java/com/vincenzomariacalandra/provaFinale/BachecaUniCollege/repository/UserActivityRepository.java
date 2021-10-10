package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
	
	Optional<UserActivity> findByActivityAndOrganizer(Activity activity, boolean organizer);

}
