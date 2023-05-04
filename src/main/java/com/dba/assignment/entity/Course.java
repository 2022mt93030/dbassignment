package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="courses")
public class Course implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4270817061490690310L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="TUTOR")
	private String tutor;
	
	@Column(name="IS_OPTIONAL")
	private Boolean isOptional;
	
	@Column(name="IS_DISABLED")
	private Boolean isDisabled;
		
	@ManyToOne
	@JoinColumn(name="SKILLS_ID")
	private Skill skill;
}
