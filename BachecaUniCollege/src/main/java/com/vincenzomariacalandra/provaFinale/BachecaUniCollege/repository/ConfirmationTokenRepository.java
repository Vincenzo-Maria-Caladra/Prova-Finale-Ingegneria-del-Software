package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.ConfirmationToken;


/**
 * @author VectorCode
 *
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	
	//Return a confirmation token, if exists, by the token code
	Optional<ConfirmationToken> findByToken(String token);
	
	//Update confirmation token confirmation, by token code
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
    
    
}
