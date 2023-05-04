package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the service_log_contains database table.
 * 
 */
@Data
@NoArgsConstructor
@Embeddable
public class ServiceLogContainPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SERVICE_LOG_ID"/*, insertable=false, updatable=false*/)
	private Long serviceLogId;

	@Column(name="PROJECT_ID"/*, insertable=false, updatable=false*/)
	private Long projectId;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ServiceLogContainPK)) {
			return false;
		}
		ServiceLogContainPK castOther = (ServiceLogContainPK)other;
		return 
			this.serviceLogId.equals(castOther.serviceLogId)
			&& this.projectId.equals(castOther.projectId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.serviceLogId.hashCode();
		hash = hash * prime + this.projectId.hashCode();
		
		return hash;
	}
}