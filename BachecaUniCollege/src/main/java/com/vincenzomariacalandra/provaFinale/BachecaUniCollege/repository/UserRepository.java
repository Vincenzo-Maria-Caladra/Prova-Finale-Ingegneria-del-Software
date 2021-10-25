package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.AppUser;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<AppUser, Long> {
	
	
	  Optional<List<AppUser>> findByName(String name);
	  
	  Optional<List<AppUser>> findBySurname(String surname);
	  
	  Optional<AppUser> findById(Long id);
	  
	  Optional<AppUser> findByEmail(String email);
	  
	  Optional<List<AppUser>> findByUserType(UserType type);
	  
	  Optional<AppUser> findByUsername(String username);
	  
	    @Transactional
	    @Modifying
	    @Query("UPDATE AppUser a " +
	            "SET a.enabled = TRUE WHERE a.email = ?1")
	    int enableAppUser(String email);

		Optional<List<AppUser>> findAllByUserType(UserType userType);
}
