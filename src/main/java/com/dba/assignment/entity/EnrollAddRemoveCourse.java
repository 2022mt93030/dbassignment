package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="enroll_or_add_remove_courses")
public class EnrollAddRemoveCourse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7251145327422431825L;

	@EmbeddedId
	private EnrollAddRemoveCoursePK id;
	
	@Column(name="IS_ENROLLED")
	private Boolean is_enrolled;
	
	@Column(name="IS_ADDED")
	private Boolean is_added;
	
	@Column(name="IS_REMOVED")
	private Boolean is_removed;
	
	@ManyToOne
    @MapsId("empId")
    @JoinColumn(name = "EMP_ID")
	private Employee emp;
	
	@ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "COURSE_ID")
	private Course course;
	
}
