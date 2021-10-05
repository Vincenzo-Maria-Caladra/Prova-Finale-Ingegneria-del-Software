package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.User;
import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility.UserType;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	/*
	 * Optional<List<User>> findByName(String name);
	 * 
	 * Optional<List<User>> findBySurname(String surname);
	 * 
	 * Optional<User> findById(Long id);
	 * 
	 * Optional<User> findByEmail(String email);
	 * 
	 * Optional<List<User>> findByTutor(long id);
	 * 
	 * Optional<List<User>> findByUserType(UserType type);
	 */
 	
}
