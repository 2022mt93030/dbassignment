package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the country database table.
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="country")
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7159183137644532035L;

	@Id	
	@Column(name="COUNTRY_ABBR")
	private String countryAbbr;

	@Column(name="CURRENCY")
	private String currency;

}