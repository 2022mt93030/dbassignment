package com.dba.assignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseDto {
	
	@NotNull
	private String title;
	private String description;
	private String tutor;
	private Boolean isOptional;
}
