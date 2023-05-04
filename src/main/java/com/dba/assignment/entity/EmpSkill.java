package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the emp_skills database table.
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="emp_skills")
@NamedQuery(name="EmpSkill.findAll", query="SELECT e FROM EmpSkill e")
public class EmpSkill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpSkillPK id;

	@ManyToOne
    @MapsId("empId")
    @JoinColumn(name = "EMP_ID")
	private Employee emp;
	
	@ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "SKILL_ID")
	private Skill skill;
	
	
	@Column(name="PROFICIENCY")
	private int proficiency;

}