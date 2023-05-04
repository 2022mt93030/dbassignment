package com.dba.assignment.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SkillsDto {

	private List<EmployeeSkillDto> empSkills = new ArrayList<>();
	
}
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//class EmployeeSkillDto {
//	
//	private int skillId;
//	
//	private int proficiency;
//	
//}
