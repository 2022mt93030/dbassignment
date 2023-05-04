package com.dba.assignment.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidateVacancyDto {
	
	private String name;
	private String education;
	private Double exp;
}
