package com.dba.assignment.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="First_name")
	private String firstName;
	@Column(name="Last_name")
	private String lastName;
	@Column(name="Gender")
	private String gender;
	@Column(name="Date_of_birth")
	private Date dateOfBirth;
	@Column(name="Nationality")
	private String nationality;
	@Column(name="Email_id")    
	private String emailId;
}
