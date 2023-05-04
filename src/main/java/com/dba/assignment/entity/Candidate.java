package com.dba.assignment.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="candidate")
public class Candidate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7879888844323332298L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="EDUCATION")
	private String education;
	
	@Column(name="EXPERIENCE")
	private Double exp;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "CANDIDATE_ID"), inverseJoinColumns = @JoinColumn(name = "SKILL_ID"))
	private Set<Skill> skills = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="VACANCY_ID")
	private Vacancy vacancy;
}
