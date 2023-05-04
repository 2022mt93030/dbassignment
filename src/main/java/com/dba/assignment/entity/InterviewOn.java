package com.dba.assignment.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="interview_on")
public class InterviewOn implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -5580299142759260567L;
	
	@EmbeddedId
	private InterviewOnPK id;
	
	@Column(name="INTERVIEW_DATE")
	private Date interviewDate;
	
	@Column(name="STATUS")
	private Boolean status; 

	@Column(name="FEEDBACK")
	private String feedback;
	
	
}
