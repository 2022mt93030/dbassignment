package com.dba.assignment.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PersonDto {

	
	private String name;	
	
	private String title;	
	
	private Date dateOfBirth;	
	
	private Date dateOfJoining;	
	
	private String education;
	
	private double salary;
	
	private Integer deptid;
	
	private Long supervisorId;
	
	private Long projectId;
	
	private String userName;
	
	private String password;
	
	private String emailId;
	
	private List<Integer> roleIds;
}
