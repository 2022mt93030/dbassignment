package com.dba.assignment.entity;

import java.io.Serializable;
import java.util.Date;

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
@Entity
@NoArgsConstructor
@Table(name="project")
public class Project implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -2457485447108526583L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CLIENT")
	private String client;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="START_DATE")
	private Date startDate;
	
	@Column(name="END_DATE")
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Dept dept;

	public Project(Long id, String name, String client, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.name = name;
		this.client = client;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}
