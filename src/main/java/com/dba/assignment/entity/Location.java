package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="location")
public class Location implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7907239284613930632L;

	
	@EmbeddedId
	private LocationPK id;
	
	@NotNull
	@Column(name="CITY")
	private String city;	
		
	@OneToOne
	@JoinColumn(name = "LCOUNTRY", insertable = false, updatable = false)
	private Country country;
}
