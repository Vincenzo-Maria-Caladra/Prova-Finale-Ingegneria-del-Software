package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vincenzomariacalandra.provaFinale.BachecaUniCollege.model.User;

@Repository
public interface MainRepository extends CrudRepository<User, Long> {}