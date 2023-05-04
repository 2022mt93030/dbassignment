package com.dba.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Location;
import com.dba.assignment.entity.LocationPK;

@Repository
public interface LocationRepository extends JpaRepository<Location, LocationPK> {

	@Query("SELECT l FROM Location l WHERE l.id.countryABBR = :countryAbbr")
	List<Location> findByCountry(@Param("countryAbbr") String countryAbbr);
}
