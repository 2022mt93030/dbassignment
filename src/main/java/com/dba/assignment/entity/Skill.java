package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the skills database table.
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="skills")
@NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s")
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;

	@Column(name="NAME")
	private String name;
	
    public Skill(String name) {
    	this.name = name;
    }
	
	//bi-directional many-to-one association to EmpSkill
//	@OneToMany(mappedBy="skill2")
//	private List<EmpSkill> empSkills;

}