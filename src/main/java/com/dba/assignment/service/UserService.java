package com.dba.assignment.service;

import com.dba.assignment.dto.UserDto;
import com.dba.assignment.entity.User;
import com.dba.assignment.entity.UserCredentials;

public interface UserService {
//	public void saveUser(UserDto user);
	public UserCredentials findUserByUserName(String userName);
	public User findUserByEmail(String email);

}
