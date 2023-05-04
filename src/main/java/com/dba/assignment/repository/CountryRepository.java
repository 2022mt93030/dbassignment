package com.dba.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

}
