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
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the service_log database table.
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="service_log")
@NamedQuery(name="ServiceLog.findAll", query="SELECT s FROM ServiceLog s")
public class ServiceLog implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5918423630615486246L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name="SERVICE_LOG_ID")
	private Set<ServiceLogContain> serviceLogProjects = new HashSet<>();

}