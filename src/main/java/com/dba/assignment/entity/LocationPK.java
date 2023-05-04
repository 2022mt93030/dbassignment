package com.dba.assignment.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LocationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5528789391066221610L;

	@NotNull
	@Column(name="LCOUNTRY"/*, insertable=false, updatable=false*/)
	private String countryABBR;

	@NotNull
	@Column(name="PIN"/*, insertable=false, updatable=false*/)
	private Integer pin;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationPK other = (LocationPK) obj;
		if (countryABBR == null) {
			if (other.countryABBR != null)
				return false;
		} else if (!countryABBR.equals(other.countryABBR))
			return false;
		if (pin != other.pin)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryABBR == null) ? 0 : countryABBR.hashCode());
		result = prime * result + pin;
		return result;
	}
	
}
