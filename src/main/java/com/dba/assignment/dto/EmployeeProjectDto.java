package com.dba.assignment.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeProjectDto {

	private Date projectJoinDate;	
	private Boolean isAllocation;
	
	private String grade;
	private String comments;
}
