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
@Table(name="vacancy")
public class Vacancy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6252288428660099979L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="STATUS")
	private boolean status;
	
	@Column(name="COUNT")
	private int count;
	
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Dept dept;

	public Vacancy(long id, String title, String description, boolean status, int count) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.count = count;
	}
	
	
}
