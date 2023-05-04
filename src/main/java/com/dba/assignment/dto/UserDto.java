package com.dba.assignment.dto;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String userName;
	private String password;
}
