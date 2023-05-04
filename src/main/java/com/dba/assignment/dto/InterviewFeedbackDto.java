package com.dba.assignment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterviewFeedbackDto {

	private Long candidateId;
	private Long employeeInterviewed;
	private Integer level;
	private String feedback;
	private boolean status;	
}
