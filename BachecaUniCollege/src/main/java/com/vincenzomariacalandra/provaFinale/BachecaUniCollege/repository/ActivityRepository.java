package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.Activity;

@Repository   
public interface ActivityRepository extends CrudRepository<Activity, Long>{

}
