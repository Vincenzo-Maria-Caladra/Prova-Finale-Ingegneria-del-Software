package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.entity.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<AppUser, Long> {

	//Return a user, if exists, by user's id
	Optional<AppUser> findById(Long id);

	//Return a user, if exists, by user's email
	Optional<AppUser> findByEmail(String email);

	//Update a user, if exists, by email
	@Transactional
	@Modifying
	@Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.email = ?1")
	int enableAppUser(String email);

	//Return a list of users, if they exist, by user's type
	List<AppUser> findAllByUserType(UserType userType);

	//Return a list of users, if they exist, by user's tutor
	@Query("SELECT a " + "FROM AppUser a " + "WHERE a.tutor = ?1")
	List<AppUser> findAllByTutor(AppUser appUser);
}
