package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the emp_skills database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmpSkillPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EMP_ID"/*, insertable=false, updatable=false*/)
	private Long empId;

	@Column(name="SKILL_ID"/*, insertable=false, updatable=false*/)
	private Integer skillId;

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmpSkillPK)) {
			return false;
		}
		EmpSkillPK castOther = (EmpSkillPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& (this.skillId == castOther.skillId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.skillId;
		
		return hash;
	}
}