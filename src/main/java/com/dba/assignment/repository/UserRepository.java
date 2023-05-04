package com.dba.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.User;
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	public User findUserByEmailId(String email);

}
