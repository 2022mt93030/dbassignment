package com.dba.assignment.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDto {
	
	private Long id;
		
	private String name;
		
	private String client;
		
	private String description;
		
	private Date startDate;
		
	private Date endDate;
	
//	private int deptId;
}
