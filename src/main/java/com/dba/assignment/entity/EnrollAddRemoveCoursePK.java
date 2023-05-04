package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnrollAddRemoveCoursePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7750723359858332348L;
	
	@Column(name="EMP_ID", insertable=false, updatable=false)
	private Long empId;
	
	@Column(name="COURSE_ID", insertable=false, updatable=false)
	private Integer courseId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrollAddRemoveCoursePK other = (EnrollAddRemoveCoursePK) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		return result;
	}
	
	

}
