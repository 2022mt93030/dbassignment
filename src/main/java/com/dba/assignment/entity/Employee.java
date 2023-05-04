package com.dba.assignment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="employee")
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DOB")
	private Date dateOfBirth;
	
	@Column(name="DOJ")
	private Date dateOfJoining;
	
	@Column(name="EDUCATION")
	private String education;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="SALARY")
	private Double salary;
	
	@ManyToOne
	@JoinColumn(name = "DEPT_ID")
	private Dept department;
	
	@ManyToOne
	@JoinColumn(name = "SID")
	private Employee supervisor;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinTable(name = "emp_roles", joinColumns = @JoinColumn(name = "EMP_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles = new HashSet<>();
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "works_on", joinColumns = @JoinColumn(name = "EMP_ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
	private Set<Project> projects = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn(name="EMP_ID")
	private Set<EnrollAddRemoveCourse> courses = new HashSet<>();
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="EMP_ID")
	private Set<EmpSkill> skillMatrix = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="SERVICE_LOG_ID")
	private ServiceLog serviceInfo;	
}
