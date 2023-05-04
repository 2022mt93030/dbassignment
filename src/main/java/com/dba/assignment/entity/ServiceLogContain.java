package com.dba.assignment.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the service_log_contains database table.
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="service_log_contains")
@NamedQuery(name="ServiceLogContain.findAll", query="SELECT s FROM ServiceLogContain s")
public class ServiceLogContain implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ServiceLogContainPK id;
	
	@ManyToOne
    @MapsId("serviceLogId")
    @JoinColumn(name = "SERVICE_LOG_ID")
	private ServiceLog serviceLog;
	
	@ManyToOne
    @MapsId("projectId")
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	@Column(name="`COMMENT`")
	private String comment;

	@Temporal(TemporalType.DATE)
	@Column(name="PROJECT_EXIT_DATE")
	private Date projectExitDate;

	@Temporal(TemporalType.DATE)
	@Column(name="PROJECT_JOIN_DATE")
	private Date projectJoinDate;
	
	@Column(name="GRADE")
	private String grade;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceLogContain other = (ServiceLogContain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	

}