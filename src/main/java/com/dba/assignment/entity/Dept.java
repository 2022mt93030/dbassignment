package com.dba.assignment.entity;

import java.io.Serializable;
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
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the dept database table.
 * 
 */
@Data
@Entity
@Table(name="dept")
@NoArgsConstructor
@NamedQuery(name="Dept.findAll", query="SELECT d FROM Dept d")
public class Dept implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public Dept(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;

	@Column(name="NAME")
	private String name;


	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "dept_exists_at", joinColumns = @JoinColumn(name = "DEPT_ID"), inverseJoinColumns = {@JoinColumn(name = "COUNTRY"),@JoinColumn(name = "PIN")} )
	private Set<Location> locations = new HashSet<>();
}