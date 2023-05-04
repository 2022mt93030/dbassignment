package com.dba.assignment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VacancyDto {

	private String title;
	private String description;
	private int count;
	private boolean status;

}
