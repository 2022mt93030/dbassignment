package com.dba.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.User;
import com.dba.assignment.entity.UserCredentials;
@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long>{
	public UserCredentials findUserByUserName(String username);

}
